package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Asynchronous pull request merge result details. */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = GitHubPullRequestMergeAsyncResultDetailsMessage.class)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubPullRequestMergeAsyncResultDetailsAccepted.class),
    @JsonSubTypes.Type(GitHubPullRequestMergeAsyncResultDetailsMessage.class),
    @JsonSubTypes.Type(GitHubPullRequestMergeAsyncResultDetailsMerged.class)
})
@CatalogSchema("components/schemas/pull-request-merge-async-result/properties/details")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubPullRequestMergeAsyncResultDetails
        permits GitHubPullRequestMergeAsyncResultDetailsAccepted,
                GitHubPullRequestMergeAsyncResultDetailsMessage,
                GitHubPullRequestMergeAsyncResultDetailsMerged {}
