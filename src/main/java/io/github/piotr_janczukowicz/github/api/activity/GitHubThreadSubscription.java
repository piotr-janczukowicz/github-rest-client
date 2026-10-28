package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Thread subscription.
 *
 * @param subscribed Whether to receive notifications from the thread
 * @param ignored Whether all notifications from the thread are blocked
 * @param reason The reason the subscription was set
 * @param createdAt Time the subscription was created
 * @param url The subscription url
 * @param threadUrl The thread url
 * @param repositoryUrl The repository url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/thread-subscription")
@RecordBuilder
public record GitHubThreadSubscription(
        boolean subscribed,
        boolean ignored,
        @Nullable String reason,
        @Nullable Instant createdAt,
        String url,
        @Nullable String threadUrl,
        @Nullable String repositoryUrl) {

    public GitHubThreadSubscription {
        requireNonNull(url, "url");
    }
}
