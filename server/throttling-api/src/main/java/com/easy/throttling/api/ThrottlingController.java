package com.easy.throttling.api;

import com.easy.throttling.common.anatation.Throttling;
import com.easy.throttling.common.exceptions.ThrottlingOverException;
import com.easy.throttling.common.interfaces.services.ThrottleAble;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Controller contains one method for test task
 */
@Controller
public class ThrottlingController implements ThrottleAble {


  /**
   * Returns a test data
   */
  @GetMapping(value = "/")
  @ResponseBody
  @Throttling(value = 50)
  public ResponseEntity<String> get() throws IOException {
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
        .body("Hello");
  }

  /**
   * handle exception of ThrottlingOverException
   *
   * @param exc
   * @return error's response
   */
  @ExceptionHandler(ThrottlingOverException.class)
  public ResponseEntity<?> handleStorageFileNotFound(ThrottlingOverException exc) {
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
  }


}