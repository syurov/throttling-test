package com.easy.throttling.common.dto;

/**
 * Created by syurov on 12/5/2018.
 */
public class ObjectMethod {

  Object object;

  String method;

  public ObjectMethod(Object object, String method) {
    this.object = object;
    this.method = method;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ObjectMethod that = (ObjectMethod) o;

    return method.equals(that.method) && object.equals(that.object);
  }

  @Override
  public int hashCode() {
    int result = object.hashCode();
    result = 31 * result + method.hashCode();
    return result;
  }
}
