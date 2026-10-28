package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Person who created a Git tag.
 *
 * @param date Date
 * @param email Email
 * @param name Name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-tag/properties/tagger")
public record GitHubGitTagTagger(String date, String email, String name) {

    public GitHubGitTagTagger {
        requireNonNull(date, "date");
        requireNonNull(email, "email");
        requireNonNull(name, "name");
    }
}
