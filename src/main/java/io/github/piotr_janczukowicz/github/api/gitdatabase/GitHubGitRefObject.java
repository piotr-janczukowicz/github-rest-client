package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Git object a reference points to.
 *
 * @param type Type
 * @param sha SHA for the reference
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-ref/properties/object")
public record GitHubGitRefObject(String type, String sha, String url) {

    private static final String PARAM_SHA = "sha";

    public GitHubGitRefObject {
        requireNonNull(type, "type");
        sha = requireMaxLength(requireMinLength(sha, 40, PARAM_SHA), 40, PARAM_SHA);
        requireNonNull(url, "url");
    }
}
