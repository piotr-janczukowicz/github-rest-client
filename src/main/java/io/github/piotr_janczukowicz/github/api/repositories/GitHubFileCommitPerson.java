package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * File commit author or committer.
 *
 * @param date Date
 * @param name Name
 * @param email Email
 */
@CatalogSchema("components/schemas/file-commit/properties/commit/properties/author")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubFileCommitPerson(
        @Nullable String date,
        @Nullable String name,
        @Nullable String email) {}
