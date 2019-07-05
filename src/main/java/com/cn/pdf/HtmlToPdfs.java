package com.cn.pdf;

import io.woo.htmltopdf.HtmlToPdf;
import io.woo.htmltopdf.HtmlToPdfException;
import io.woo.htmltopdf.HtmlToPdfObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author hebiao
 * @version :HtmlToPdfs.java, v0.1 2019/6/28 18:02 hebiao Exp $$
 */
public class HtmlToPdfs {

    public static void main(String[] args) {
        HtmlToPdf htmlToPdf = HtmlToPdf.create()
                // ...
                .object(HtmlToPdfObject.forUrl("https://www.sellersprite.com/v2/market-research"));

        try (InputStream in = htmlToPdf.convert()) {
            File pdf = new File("D://test.pdf");
            OutputStream outputStream = new FileOutputStream(pdf);

            int t =0;
            byte[] buffer = new byte[1024];
            while((t=in.read(buffer))!=-1){
                outputStream.write(buffer,0,t);
            }
            in.close();
            outputStream.close();
            // "in" has PDF bytes loaded
        } catch (HtmlToPdfException e) {
            // HtmlToPdfException is a RuntimeException, thus you are not required to
            // catch it in this scope. It is thrown when the conversion fails
            // for any reason.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
