package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Git author or committer of a commit.
 *
 * @param name Name of the commit's author or committer
 * @param email Git email address of the commit's author or committer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/simple-commit/properties/author",
    "components/schemas/simple-commit/properties/committer"
})
public record GitHubSimpleCommitPerson(String name, String email) {

    public GitHubSimpleCommitPerson {
        requireNonNull(name, "name");
        requireNonNull(email, "email");
    }
}
