package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Webhook delivery response body.
 *
 * @param headers Headers
 * @param payload Payload
 */
@CatalogSchema("components/schemas/hook-delivery/properties/response")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubHookDeliveryResponseMessage(
        @Nullable OptionalNull<Map<String, Object>> headers,
        @Nullable OptionalNull<String> payload) {}
