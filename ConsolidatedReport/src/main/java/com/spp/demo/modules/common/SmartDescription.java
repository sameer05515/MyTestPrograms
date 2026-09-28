package com.spp.demo.modules.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmartDescription {

  private String content; // The description content
  private String textOutputType; // Output type, e.g., "markdown" or "html"
  private String textInputType; // Input type, e.g., "TextArea" or "CKEditor"
}
