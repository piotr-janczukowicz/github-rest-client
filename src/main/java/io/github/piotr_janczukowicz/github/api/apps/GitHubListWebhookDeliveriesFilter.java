package io.github.piotr_janczukowicz.github.api.apps;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookDeliveryStatus;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /app/hook/deliveries}, excluding pagination.
 *
 * @param status Returns webhook deliveries filtered by delivery outcome classification based on {@code status_code}
 *     range.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/list-webhook-deliveries/parameters")
public record GitHubListWebhookDeliveriesFilter(@Nullable GitHubWebhookDeliveryStatus status) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListWebhookDeliveriesFilter empty() {
        return GitHubListWebhookDeliveriesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (status != null) {
            query.put("status", status.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
