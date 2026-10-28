package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Git object a tag points to.
 *
 * @param sha Sha
 * @param type Type
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-tag/properties/object")
public record GitHubGitTagObject(String sha, String type, String url) {

    public GitHubGitTagObject {
        requireNonNull(sha, "sha");
        requireNonNull(type, "type");
        requireNonNull(url, "url");
    }
}
