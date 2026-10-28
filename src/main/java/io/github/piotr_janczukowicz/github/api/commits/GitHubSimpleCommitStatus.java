package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Simple commit status.
 *
 * @param description Description
 * @param id Id
 * @param nodeId Node id
 * @param state State
 * @param context Context
 * @param targetUrl Target url
 * @param required Required
 * @param avatarUrl Avatar url
 * @param url Url
 * @param createdAt Created at
 * @param updatedAt Updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/simple-commit-status")
@RecordBuilder
public record GitHubSimpleCommitStatus(
        @Nullable String description,
        long id,
        String nodeId,
        String state,
        String context,
        @Nullable String targetUrl,
        @Nullable Boolean required,
        @Nullable String avatarUrl,
        String url,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubSimpleCommitStatus {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(state, "state");
        requireNonNull(context, "context");
        requireNonNull(url, "url");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
