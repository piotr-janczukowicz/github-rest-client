package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Simple webhook delivery.
 *
 * @param id Id
 * @param guid Guid
 * @param deliveredAt Delivered at
 * @param redelivery Redelivery
 * @param duration Duration
 * @param status Status
 * @param statusCode Status code
 * @param event Event
 * @param action Action
 * @param installationId Installation id
 * @param repositoryId Repository id
 * @param throttledAt Throttled at
 */
@CatalogSchema("components/schemas/hook-delivery-item")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubHookDeliveryItem(
        long id,
        String guid,
        Instant deliveredAt,
        boolean redelivery,
        double duration,
        String status,
        int statusCode,
        String event,
        @Nullable String action,
        @Nullable Long installationId,
        @Nullable Long repositoryId,
        @Nullable Instant throttledAt) {
    public GitHubHookDeliveryItem {
        requireNonNull(guid, "guid");
        requireNonNull(deliveredAt, "deliveredAt");
        requireNonNull(status, "status");
        requireNonNull(event, "event");
    }
}
