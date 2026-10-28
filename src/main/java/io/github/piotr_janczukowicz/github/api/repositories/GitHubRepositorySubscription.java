package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Repository subscription.
 *
 * @param subscribed Determines if notifications should be received from this repository
 * @param ignored Determines if all notifications should be blocked from this repository
 * @param reason The reason the subscription was set
 * @param createdAt Time the subscription was created
 * @param url The subscription url
 * @param repositoryUrl The repository url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/repository-subscription")
@RecordBuilder
public record GitHubRepositorySubscription(
        boolean subscribed,
        boolean ignored,
        @Nullable String reason,
        Instant createdAt,
        String url,
        String repositoryUrl) {

    public GitHubRepositorySubscription {
        requireNonNull(createdAt, "createdAt");
        requireNonNull(url, "url");
        requireNonNull(repositoryUrl, "repositoryUrl");
    }
}
