package io.github.piotr_janczukowicz.github.api.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Text match metadata for one searchable property on a search result item.
 *
 * @param objectUrl URL of the object containing the matched property
 * @param objectType Object type, when present
 * @param property Property name that matched
 * @param fragment Property value fragment containing the match
 * @param matches Highlighted substrings inside {@code fragment}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/search-result-text-matches/items")
public record GitHubSearchTextMatchItem(
        @Nullable String objectUrl,
        @Nullable String objectType,
        @Nullable String property,
        @Nullable String fragment,
        @Nullable List<GitHubSearchTextMatch> matches) {}
