package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create organization webhook ({@code orgs/create-webhook}).
 *
 * @param name Name
 * @param config Config
 * @param events Events
 * @param active Active
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/create-webhook/requestBody")
public record GitHubCreateOrgWebhookPayload(
        String name,
        GitHubCreateOrgWebhookConfig config,
        @Nullable List<String> events,
        @Nullable Boolean active) {

    public GitHubCreateOrgWebhookPayload {
        requireNonNull(name, "name");
        requireNonNull(config, "config");
        if (events != null) {
            events = List.copyOf(events);
        }
    }
}
