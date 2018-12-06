package com.easy.throttling.services.aspect;


import com.easy.throttling.common.annotations.Throttling;
import com.easy.throttling.common.interfaces.services.ThrottleAble;

/**
 * Created by syurov on 12/5/2018.
 */
@Throttling
public class TestClass implements ThrottleAble {

  @Throttling
  public String testMethod() {

    return "";
  }
}
