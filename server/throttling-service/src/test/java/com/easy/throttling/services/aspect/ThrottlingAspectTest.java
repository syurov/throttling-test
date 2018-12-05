package com.easy.throttling.services.aspect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by syurov on 12/5/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ThrottlingAspectTest.Config.class})
public class ThrottlingAspectTest {

  @Autowired
  @Qualifier("TestClass")
  TestClass testClass;

  @Autowired
  @Qualifier("TestClass2")
  TestClass testClass2;

  @Autowired
  @Qualifier("TestClass3")
  TestClass testClass3;

  @Autowired
  @Qualifier("TestClass4")
  TestClass testClass4;

  @Test
  public void testLessThan50() {

    for (int i = 0; i < 45; i++)
      testClass.testMethod();

  }

  @Test
  public void testMoreThan50() throws Exception {

    try {
      for (int i = 0; i < 55; i++)
        testClass2.testMethod();

    } catch (Exception ignored) {
      return;
    }

    throw new Exception("expected error of over");
  }

  @Test
  public void testCoupleLessThan50() {

    for (int i = 0; i < 45; i++) {
      testClass3.testMethod();
      testClass4.testMethod();
    }
  }


  @Configuration
  @EnableAspectJAutoProxy(
      proxyTargetClass = true
  )
  @ComponentScan(value = {
      "com.easy.throttling.services.aspect",
  }, excludeFilters = {
      @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Configuration.class})})
  public static class Config {

    @Bean
    @Qualifier("TestClass")
    TestClass getTestClass() {
      return new TestClass();
    }

    @Bean
    @Qualifier("TestClass2")
    TestClass getTestClass2() {
      return new TestClass();
    }

    @Bean
    @Qualifier("TestClass3")
    TestClass getTestClass3() {
      return new TestClass();
    }

    @Bean
    @Qualifier("TestClass4")
    TestClass getTestClass4() {
      return new TestClass();
    }
  }
}
