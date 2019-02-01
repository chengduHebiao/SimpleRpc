/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.cn.netty.HttpServer.domain.HRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpChunkedInput;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;
import javax.activation.MimetypesFileTypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author hebiao
 * @version $Id:HttpServerHandler.java, v0.1 2019/1/29 16:19 hebiao Exp $$
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 60;

    private static Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            String method = request.method().name();
            String url = request.uri().toString();
            String body = getBody(request);
            logger.warn("httpRequest:method: {},url:{},body:{}", method, url, body);

            String result;
            if (StringUtils.isEmpty(url)) {
                result = "not found";
                send(ctx, result, HttpResponseStatus.NOT_FOUND);
                return;
            }

            if (HttpMethod.GET.equals(request.method())) {
                result = "get请求";
                if (url.contains("/?dir")) {
                    doFilePath(request, ctx);
                    return;
                }
                send(ctx, result, HttpResponseStatus.OK);
                return;
            }
            if (HttpMethod.POST.equals(request.method())) {
                result = "post请求";
                HttpHeaders headers = request.headers();
                Iterator<Entry<String, String>> iterator = headers.iteratorAsString();
                while (iterator.hasNext()) {
                    Entry<String, String> entry = iterator.next();
                    logger.warn("header:" + entry.getKey() + "=" + entry.getValue() + "\r\n");
                }

                send(ctx, result, HttpResponseStatus.OK);
                return;
            }


        } catch (Throwable e) {
            logger.error(e.getMessage());
            send(ctx, "", HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }

        super.channelRead(ctx, msg);
    }

    /**
     * 　  文件服务器使用HTTP协议对外提供服务 　　当客户端通过浏览器访问文件服务器时，对访问路径进行检查，检查失败返回403 　 　 检查通过，以链接的方式打开当前文件目录，每个目录或者都是个超链接，可以递归访问
     * 如果是目录，可以继续递归访问它下面的目录或者文件，如果是文件并且可读，则可以在浏览器端直接打开，或者通过[目标另存为]下载
     */
    private void doFilePath(FullHttpRequest request, ChannelHandlerContext channelHandlerContext)
            throws UnsupportedEncodingException {

        String uri = URLDecoder.decode(request.uri(),"UTF-8");;
        String filePath = uri.substring(uri.indexOf("=")+1, uri.length());
        if (StringUtils.isEmpty(filePath)) {
            send(channelHandlerContext, "path is null", HttpResponseStatus.BAD_REQUEST);
        }

        filePath = filePath.equals("/") ? "E:\\" : filePath;//默认找E盘的根路径
        File file = new File(filePath);
        if (!file.exists()) {
            send(channelHandlerContext, "cant find file", HttpResponseStatus.FORBIDDEN);
        }

        StringBuilder textBuilder = new StringBuilder("<html>\r\n")
                .append("<head>\r\n")
                .append("<title>").append("files").append("</title>\r\n").append("</head>\r\n")
                .append("<body>\r\n");

        if (file.isDirectory()) {
            logger.info("开始遍历文件夹：{}",file.getAbsolutePath());
            textBuilder.append("<ul>\r\n");
            File[] childFiles = file.listFiles();
            for (File child : childFiles) {
                String absolutePath = child.getAbsolutePath();
                textBuilder.append("<li>").append
                        ("<a href='").append("/?dir=/"+absolutePath).append("'>").append(absolutePath).append
                        ("</a>")
                        .append
                        ("</li>\r\n");

            }
            textBuilder.append("</ul>\r\n");

        } else {
            if (file.canRead()) {
                try {
                    logger.info("打开文件:{}",file.getAbsolutePath());
                 /*   BufferedReader reader = new BufferedReader(new FileReader(file));
                    textBuilder.append("<h>");
                    while (reader.read() != -1) {
                        textBuilder.append(reader.readLine() + "\r\n");
                    }
                    textBuilder.append("</h>\r\n");

*/
                    //
                    HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
                    HttpUtil.setContentLength(response, file.length());
                    setContentTypeHeader(response, file);
                    setDateAndCacheHeaders(response, file);
                    if (HttpUtil.isKeepAlive(request)) {
                        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                    }
                    channelHandlerContext.write(response);
                    // Write the content.
                    ChannelFuture sendFileFuture;
                    ChannelFuture lastContentFuture;
                    long fileLength = file.length();
                    if (channelHandlerContext.pipeline().get(SslHandler.class) == null) {
                        sendFileFuture =
                                channelHandlerContext.write(new DefaultFileRegion(file, 0, fileLength),
                                        channelHandlerContext.newProgressivePromise());
                        // Write the end marker.
                        lastContentFuture = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                    } else {
                        sendFileFuture =
                                channelHandlerContext.writeAndFlush(new HttpChunkedInput(new ChunkedFile(file, 8192)),
                                        channelHandlerContext.newProgressivePromise());
                        // HttpChunkedInput will write the end marker (LastHttpContent) for us.
                        lastContentFuture = sendFileFuture;
                    }

                    sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
                        @Override
                        public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                            if (total < 0) { // total unknown
                                System.err.println(future.channel() + " Transfer progress: " + progress);
                            } else {
                                System.err.println(future.channel() + " Transfer progress: " + progress + " / " + total);
                            }
                        }

                        @Override
                        public void operationComplete(ChannelProgressiveFuture future) {
                            System.err.println(future.channel() + " Transfer complete.");
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                send(channelHandlerContext, "file cant read", HttpResponseStatus.BAD_REQUEST);
            }

        }
        textBuilder.append("<body>\r\n").append("</html>");
        ByteBuf content = Unpooled.copiedBuffer
                (textBuilder, CharsetUtil.UTF_8);
        HttpHeaders headers = new DefaultHttpHeaders();
        headers.add(HttpHeaderNames.DATE, LocalDateTime.now());
        headers.add(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        headers.add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content,
                headers, headers);

        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    private void send(ChannelHandlerContext ctx, String result, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer
                (result, CharsetUtil.UTF_8));
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private String getBody(FullHttpRequest request) {
        ByteBuf byteBuf = request.content();
        return byteBuf.toString(CharsetUtil.UTF_8);

    }

    //TODO 处理调用逻辑
    private void doInvoke(HRequest request) {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();//上线
       // logger.warn("client [" + incoming.remoteAddress().toString() + "]" + "与服务端建立连接成功\n");
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        //logger.warn("client [" + incoming.remoteAddress().toString() + "]" + "下线\n");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
    }


    /**
     * Sets the Date and Cache headers for the HTTP Response
     *
     * @param response
     *            HTTP response
     * @param fileToCache
     *            file to extract content type
     */
    private static void setDateAndCacheHeaders(HttpResponse response, File fileToCache) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

        // Date header
        Calendar time = new GregorianCalendar();
        response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));

        // Add cache headers
        time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
        response.headers().set(HttpHeaderNames.EXPIRES, dateFormatter.format(time.getTime()));
        response.headers().set(HttpHeaderNames.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
        response.headers().set(
                HttpHeaderNames.LAST_MODIFIED, dateFormatter.format(new Date(fileToCache.lastModified())));
    }

    /**
     * Sets the content type header for the HTTP Response
     *
     * @param response
     *            HTTP response
     * @param file
     *            file to extract content type
     */
    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType(file.getPath()));
    }

}
