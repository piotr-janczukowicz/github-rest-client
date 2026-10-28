package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Repository webhook.
 *
 * @param type Type
 * @param id Id
 * @param name Name
 * @param active Active
 * @param events Events
 * @param config Config
 * @param updatedAt Updated at
 * @param createdAt Created at
 * @param url Url
 * @param testUrl Test url
 * @param pingUrl Ping url
 * @param deliveriesUrl Deliveries url
 * @param lastResponse Last response
 */
@CatalogSchema("components/schemas/hook")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubHook(
        String type,
        long id,
        String name,
        boolean active,
        List<String> events,
        GitHubWebhookConfig config,
        Instant updatedAt,
        Instant createdAt,
        String url,
        String testUrl,
        String pingUrl,
        @Nullable String deliveriesUrl,
        GitHubHookResponse lastResponse) {

    public GitHubHook {
        requireNonNull(type, "type");
        requireNonNull(name, "name");
        requireNonNull(events, "events");
        requireNonNull(config, "config");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(url, "url");
        requireNonNull(testUrl, "testUrl");
        requireNonNull(pingUrl, "pingUrl");
        requireNonNull(lastResponse, "lastResponse");
    }
}
