package com.p.api.node.service.base.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A standardized API response format for success and error responses.
 *
 * @param <T> The type of the data being returned.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StandardResponse<T> {

  private String status;
  private String message;
  private T data;
  private int statusCode;
  private LocalDateTime timeStamp;
}
