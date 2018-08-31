/**
 * Inc All Rights Reserved @2018
 */

package com.cn.rpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hebiao
 * @version $Id:SerializationUtil.java, v0.1 2018/5/31 21:42 hebiao Exp $$ 序列化工具
 */
public class SerializationUtil {


  public static byte[] serialize(Object object) {
    if (object == null) {
      return null;
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
    try {
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(object);
      oos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return baos.toByteArray();
  }


  public static Object deserialize(byte[] bytes) {
    try {
      ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
      return inputStream.readObject();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
}
