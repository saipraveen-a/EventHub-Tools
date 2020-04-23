package com.example.eventhub;

import com.example.eventhub.tenancy.TenancyContext;
import com.example.eventhub.tenancy.TenancyContextHolder;
import com.example.eventhub.tenancy.Tenant;

import org.springframework.messaging.Message;

public abstract class EventHandler<T> {
  public <T> void processHeaders(Message<T> message) {
    String tenantId = null, tenantAlias = null;
    if (message.getHeaders().get("tenant-id") != null) {
      tenantId = message.getHeaders().get("tenant-id").toString();
    }
    if (message.getHeaders().get("tenant-alias") != null) {
      tenantAlias = message.getHeaders().get("tenant-alias").toString();
    }
    if (tenantId != null && tenantAlias != null) {
      TenancyContextHolder.setContext(TenancyContext.newContext(new Tenant(tenantId, tenantAlias, true)));
    }
  }

  protected abstract void handleEvent(Message<T> message) throws Exception;
}
