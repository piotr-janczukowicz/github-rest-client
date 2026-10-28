package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Reaction Rollup.
 *
 * @param url Url
 * @param totalCount Total count
 * @param plusOne Plus one
 * @param minusOne Minus one
 * @param laugh Laugh
 * @param confused Confused
 * @param heart Heart
 * @param hooray Hooray
 * @param eyes Eyes
 * @param rocket Rocket
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/reaction-rollup")
public record GitHubReactionRollup(
        String url,
        int totalCount,
        @JsonProperty("+1") int plusOne,
        @JsonProperty("-1") int minusOne,
        int laugh,
        int confused,
        int heart,
        int hooray,
        int eyes,
        int rocket) {

    public GitHubReactionRollup {
        requireNonNull(url, "url");
    }
}
