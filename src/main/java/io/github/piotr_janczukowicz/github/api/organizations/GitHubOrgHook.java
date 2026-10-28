package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Organization webhook.
 *
 * @param id Id
 * @param url Url
 * @param pingUrl Ping url
 * @param deliveriesUrl Deliveries url
 * @param name Name
 * @param events Events
 * @param active Active
 * @param config Config
 * @param updatedAt Updated at
 * @param createdAt Created at
 * @param type Type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/org-hook")
@RecordBuilder
public record GitHubOrgHook(
        long id,
        String url,
        String pingUrl,
        @Nullable String deliveriesUrl,
        String name,
        List<String> events,
        boolean active,
        GitHubOrgHookConfig config,
        Instant updatedAt,
        Instant createdAt,
        String type) {

    public GitHubOrgHook {
        requireNonNull(url, "url");
        requireNonNull(pingUrl, "pingUrl");
        requireNonNull(name, "name");
        requireNonNull(events, "events");
        requireNonNull(config, "config");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(type, "type");
    }
}
