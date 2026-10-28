package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Audit log streaming configuration of an enterprise.
 *
 * @param id The unique identifier of the stream
 * @param streamType The type of the stream
 * @param streamDetails The details of the stream destination
 * @param enabled Whether the stream is enabled
 * @param createdAt The time the stream was created
 * @param updatedAt The time the stream was last updated
 * @param pausedAt The time the stream was paused
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-audit-log-stream-config")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGetAuditLogStreamConfig(
        long id,
        String streamType,
        String streamDetails,
        boolean enabled,
        Instant createdAt,
        Instant updatedAt,
        @Nullable Instant pausedAt) {

    public GitHubGetAuditLogStreamConfig {
        requireNonNull(streamType, "streamType");
        requireNonNull(streamDetails, "streamDetails");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
