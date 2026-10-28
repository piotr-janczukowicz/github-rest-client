package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Create repository dispatch event.
 *
 * @param eventType A custom webhook event name.
 * @param clientPayload JSON payload with extra details about the webhook event.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-dispatch-event/requestBody")
public record GitHubCreateDispatchEventPayload(
        String eventType, @Nullable Map<String, Object> clientPayload) {
    public GitHubCreateDispatchEventPayload {
        eventType = requireMaxLength(requireMinLength(eventType, 1, "eventType"), 100, "eventType");
        if (clientPayload != null) {
            clientPayload = Collections.unmodifiableMap(
                    new LinkedHashMap<>(requireSizeAtMost(clientPayload, 10, "clientPayload")));
        }
    }
}
