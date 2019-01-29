/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.hebiao.test;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.springframework.util.StringUtils;

/**
 * @author hebiao
 * @version $Id:HbaseTest.java, v0.1 2018/11/28 16:30 hebiao Exp $$
 */
public class HbaseTest {


    @Test
    public void testCreateTable() throws IOException {

        Configuration configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "192.168.100.3,192.168.100.4,192.168.100.5");
        //创建客户端
        Connection connection = ConnectionFactory.createConnection(configuration);

        Admin admin = connection.getAdmin();

        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("mytable"));

        //创建列族

        HColumnDescriptor h1 = new HColumnDescriptor("order");

        HColumnDescriptor h2 = new HColumnDescriptor("message");

        hTableDescriptor.addFamily(h1);

        hTableDescriptor.addFamily(h2);

        admin.createTable(hTableDescriptor);

        admin.close();
    }

    public Connection getConnection() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "192.168.100.3,192.168.100.4,192.168.100.5");
        //创建客户端
        Connection connection = ConnectionFactory.createConnection(configuration);
        return connection;

    }

    @Test
    public void testPut() throws IOException {

        Connection connection = null;
        try {
            connection = getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Table table = connection.getTable(TableName.valueOf("mytable"));

        Put put
                = new Put(Bytes.toBytes("row+1"));

        put.addColumn(Bytes.toBytes("order"), Bytes.toBytes("date"), Bytes.toBytes("2019"));
        put.addColumn(Bytes.toBytes("message"), Bytes.toBytes("error"), Bytes.toBytes("dontright"));
        put.addColumn(Bytes.toBytes("message"), Bytes.toBytes("config"), Bytes.toBytes("uiii"));

        table.put(put);

        table.close();

    }

    @Test
    public void testGet() throws IOException {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Table table = connection.getTable(TableName.valueOf("mytable"));

        Scan scan = new Scan();

        ResultScanner resultScanner = table.getScanner(scan);

        Iterator<Result> it = resultScanner.iterator();

        while (it.hasNext()) {
            Result result = it.next();
            getRow(result);
        }


    }

    private void getRow(Result result) {
        String string = new String(result.getRow());

        if (StringUtils.isEmpty(string)) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(string).append("[")
                .append("row+1:order=" + Bytes.toString(result.getValue(Bytes.toBytes("order"), Bytes.toBytes("date"))
                ) + "\t")
                .append("row+1:message=" + Bytes
                        .toString(result.getValue(Bytes.toBytes("message"), Bytes.toBytes("error"))));

        System.out.println(sb.toString());


    }
}
