package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create commit status payload.
 *
 * @param state The state of the status
 * @param targetUrl The target URL to associate with this status
 * @param description A short description of the status
 * @param context A string label to differentiate this status from other systems
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-commit-status/requestBody")
public record GitHubCreateCommitStatusPayload(
        GitHubCommitStatusState state,
        @Nullable String targetUrl,
        @Nullable String description,
        @Nullable String context) {

    public GitHubCreateCommitStatusPayload {
        requireNonNull(state, "state");
    }
}
