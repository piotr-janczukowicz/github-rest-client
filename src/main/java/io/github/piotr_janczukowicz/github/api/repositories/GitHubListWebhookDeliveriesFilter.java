package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET .../hooks/{hook_id}/deliveries}, excluding pagination.
 *
 * @param status Delivery outcome status
 */
@CatalogSchema({
    "operations/repos/list-webhook-deliveries/parameters",
    "operations/orgs/list-webhook-deliveries/parameters"
})
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
        if (status == null) {
            return Map.of();
        }
        return Map.of("status", status.wireValue());
    }
}
