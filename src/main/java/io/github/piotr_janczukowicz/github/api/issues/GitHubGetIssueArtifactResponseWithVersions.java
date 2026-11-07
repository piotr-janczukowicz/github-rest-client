package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeast;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * An issue artifact together with its versions.
 *
 * @param artifact Issue artifacts are named, typed pieces of content attached to an issue. They give people and agents
 *     a durable place to preserve context and supporting material alongside the work. Each artifact has a number unique
 *     within the issue.
 * @param hasNextPage Whether another page of retained versions is available.
 * @param totalCount The number of retained artifact versions.
 * @param versions The artifact version history, ordered from newest to oldest.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/get-issue-artifact/200/oneOf/1")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetIssueArtifactResponseWithVersions(
        GitHubIssueArtifact artifact,
        boolean hasNextPage,
        int totalCount,
        List<GitHubGetIssueArtifactResponseWithVersionsVersion> versions)
        implements GitHubGetIssueArtifactResponse {
    public GitHubGetIssueArtifactResponseWithVersions {
        requireNonNull(artifact, "artifact");
        requireAtLeast(totalCount, 0, "totalCount");
        requireNonNull(versions, "versions");
    }
}
