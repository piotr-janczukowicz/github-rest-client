package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Merge pull request asynchronously request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/merge-async/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubMergePullRequestAsyncPayload(
        @Nullable String commitTitle,
        @Nullable String commitMessage,
        @Nullable String sha,
        @Nullable GitHubPullRequestMergeMethod mergeMethod,
        @Nullable GitHubPullRequestMergeAsyncAction mergeAction,
        @Nullable Boolean bypassRules) {}
