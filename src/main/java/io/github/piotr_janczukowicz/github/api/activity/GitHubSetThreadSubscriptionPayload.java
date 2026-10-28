package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Set thread subscription request body.
 *
 * @param ignored Whether to block all notifications from a thread.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/set-thread-subscription/requestBody")
@RecordBuilder
public record GitHubSetThreadSubscriptionPayload(@Nullable Boolean ignored) {}
