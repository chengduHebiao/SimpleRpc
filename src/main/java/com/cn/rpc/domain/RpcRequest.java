/**
 *  Inc All Rights Reserved @2018
 */

package com.cn.rpc.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author hebiao
 * @version $Id:RpcRequest.java, v0.1 2018/5/31 22:00 hebiao Exp $$
 */
public class RpcRequest implements Serializable {

  private String Id;
  private String interfaceName;//远程接口名
  private String methodName;//远程方法名
  private Class<?>[] clazzTypes;//参数类型
  private Object[] parameters;//参数值

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public Class<?>[] getClazzTypes() {
    return clazzTypes;
  }

  public void setClazzTypes(Class<?>[] clazzTypes) {
    this.clazzTypes = clazzTypes;
  }

  public Object[] getParameters() {
    return parameters;
  }

  public void setParameters(Object[] parameters) {
    this.parameters = parameters;
  }

  @Override
  public String toString() {
    return "RpcRequest{" +
        "Id='" + Id + '\'' +
        ", interfaceName='" + interfaceName + '\'' +
        ", methodName='" + methodName + '\'' +
        ", clazzTypes=" + Arrays.toString(clazzTypes) +
        ", parameters=" + Arrays.toString(parameters) +
        '}';
  }
}
