package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Webhook delivery request body.
 *
 * @param headers Request headers ({@code additionalProperties: true})
 * @param payload Webhook event payload as a JSON object map
 */
@CatalogSchema("components/schemas/hook-delivery/properties/request")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubHookDeliveryMessage(
        @Nullable Map<String, Object> headers, @Nullable Map<String, Object> payload) {}
