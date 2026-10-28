package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update organization webhook ({@code orgs/update-webhook}).
 *
 * @param config Config
 * @param events Events
 * @param active Active
 * @param name Name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/update-webhook/requestBody")
public record GitHubUpdateOrgWebhookPayload(
        @Nullable GitHubUpdateOrgWebhookConfig config,
        @Nullable List<String> events,
        @Nullable Boolean active,
        @Nullable String name) {

    public GitHubUpdateOrgWebhookPayload {
        if (events != null) {
            events = List.copyOf(events);
        }
    }
}
