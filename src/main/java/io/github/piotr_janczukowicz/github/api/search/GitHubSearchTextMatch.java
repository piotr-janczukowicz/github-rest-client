package io.github.piotr_janczukowicz.github.api.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * One highlighted substring inside a search text match.
 *
 * @param text Matched text
 * @param indices Start and end indices of the match in the fragment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/search-result-text-matches/items/properties/matches/items")
public record GitHubSearchTextMatch(
        @Nullable String text, @Nullable List<Integer> indices) {}
