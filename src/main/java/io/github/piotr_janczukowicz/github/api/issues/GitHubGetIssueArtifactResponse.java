package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** The requested artifact, alone or together with its version history. */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
    @JsonSubTypes.Type(GitHubIssueArtifact.class),
    @JsonSubTypes.Type(GitHubGetIssueArtifactResponseWithVersions.class)
})
@CatalogSchema("operations/issues/get-issue-artifact/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubGetIssueArtifactResponse
        permits GitHubIssueArtifact, GitHubGetIssueArtifactResponseWithVersions {}
