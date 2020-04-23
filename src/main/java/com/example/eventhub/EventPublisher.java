package com.example.eventhub;

import com.example.eventhub.tenancy.TenancyContextHolder;
import com.example.eventhub.tenancy.Tenant;
import com.jda.security.tenancy.TenancyContextHolder;
import com.jda.security.tenancy.Tenant;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

public abstract class EventPublisher<T> {
  public boolean publish(T event) {
    MessageChannel messageChannel = getChannel();

    MessageBuilder messageBuilder = MessageBuilder.withPayload(event);
    if (isTenantAware()) {
      Tenant tenant = TenancyContextHolder.getContext().getTenant();
      messageBuilder.setHeader("tenant-id", tenant.getId());
      messageBuilder.setHeader("tenant-alias", tenant.getAlias());
    }

    boolean messageSent = messageChannel.send(messageBuilder.build());

    return messageSent;
  }

  protected abstract boolean isTenantAware();

  protected abstract MessageChannel getChannel();
}
