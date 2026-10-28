package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The status of the code search index for this repository.
 *
 * @param lexicalSearchOk Lexical search ok
 * @param lexicalCommitSha Lexical commit sha
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/repository/properties/code_search_index_status")
@RecordBuilder
public record GitHubCodeSearchIndexStatus(
        @Nullable Boolean lexicalSearchOk, @Nullable String lexicalCommitSha) {}
