package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Tag.
 *
 * @param name Name
 * @param commit Commit
 * @param zipballUrl Zipball url
 * @param tarballUrl Tarball url
 * @param nodeId Node id
 */
@CatalogSchema("components/schemas/tag")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryTag(
        String name, GitHubRepositoryTagCommit commit, String zipballUrl, String tarballUrl, String nodeId) {

    public GitHubRepositoryTag {
        requireNonNull(name, "name");
        requireNonNull(commit, "commit");
        requireNonNull(zipballUrl, "zipballUrl");
        requireNonNull(tarballUrl, "tarballUrl");
        requireNonNull(nodeId, "nodeId");
    }
}
