package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Delete file author or committer.
 *
 * @param name The name of the author (or committer) of the commit
 * @param email The email of the author (or committer) of the commit
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/delete-file/requestBody/properties/author",
    "operations/repos/delete-file/requestBody/properties/committer"
})
public record GitHubDeleteFileAuthor(
        @Nullable String name, @Nullable String email) {}
