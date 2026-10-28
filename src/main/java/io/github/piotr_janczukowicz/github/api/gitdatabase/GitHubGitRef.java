package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Git references within a repository.
 *
 * @param ref Ref
 * @param nodeId Node id
 * @param url Url
 * @param object Object
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-ref")
public record GitHubGitRef(String ref, String nodeId, String url, GitHubGitRefObject object) {

    public GitHubGitRef {
        requireNonNull(ref, "ref");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(object, "object");
    }
}
