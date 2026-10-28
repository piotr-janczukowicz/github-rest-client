package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * File contents author or committer.
 *
 * @param name The name of the author or committer of the commit
 * @param email The email of the author or committer of the commit
 * @param date Commit date
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/create-or-update-file-contents/requestBody/properties/author",
    "operations/repos/create-or-update-file-contents/requestBody/properties/committer"
})
public record GitHubFileContentsAuthor(
        String name, String email, @Nullable String date) {

    public GitHubFileContentsAuthor {
        requireNonNull(name, "name");
        requireNonNull(email, "email");
    }
}
