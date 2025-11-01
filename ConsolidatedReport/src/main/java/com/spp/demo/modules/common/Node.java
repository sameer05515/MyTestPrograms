package com.spp.demo.modules.common;

public interface Node {
  public String getUniqueId();

  public String getName();

  public String getParentId();

  default String getType() {
    return this.getClass().getSimpleName(); // Returns the class name as the type
  }
}
