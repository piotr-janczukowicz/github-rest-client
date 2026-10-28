package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Commit status.
 *
 * @param url Url
 * @param avatarUrl Avatar url
 * @param id Id
 * @param nodeId Node id
 * @param state State
 * @param description Description
 * @param targetUrl Target url
 * @param context Context
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param creator Creator
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/status")
@RecordBuilder
public record GitHubCommitStatus(
        String url,
        @Nullable String avatarUrl,
        long id,
        String nodeId,
        String state,
        @Nullable String description,
        @Nullable String targetUrl,
        String context,
        String createdAt,
        String updatedAt,
        @Nullable GitHubSimpleUser creator) {

    public GitHubCommitStatus {
        requireNonNull(url, "url");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(state, "state");
        requireNonNull(context, "context");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
