package com.example.eventhub.tenancy;

public class TenantRequiredException extends RuntimeException {

  public TenantRequiredException() {
    super("Valid tenant is required");
  }
}
