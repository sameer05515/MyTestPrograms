package com.p.api.node.service.base.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

public class HttpStatusUtil {

  public static List<HttpStatus> getHttpStatusList() {
    return Arrays.stream(HttpStatus.values())
        // .map(httpStatus -> httpStatus.value() + " " + httpStatus.getReasonPhrase())
        .collect(Collectors.toList());
  }

  public static List<String> getHttpStatusValues() {
    return getHttpStatusList().stream()
        .map(httpStatus -> httpStatus.value() + " " + httpStatus.getReasonPhrase())
        .toList();
  }
}
