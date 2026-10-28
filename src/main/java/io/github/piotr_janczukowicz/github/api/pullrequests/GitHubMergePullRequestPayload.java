package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Merge pull request request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/merge/requestBody")
public record GitHubMergePullRequestPayload(
        @Nullable String commitTitle,
        @Nullable String commitMessage,
        @Nullable String sha,
        @Nullable GitHubPullRequestMergeMethod mergeMethod) {}
