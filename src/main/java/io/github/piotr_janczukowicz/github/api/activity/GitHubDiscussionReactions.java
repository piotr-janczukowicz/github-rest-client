package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Reaction counts. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/discussion/properties/reactions")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDiscussionReactions(
        @JsonProperty("+1") int plusOne,
        @JsonProperty("-1") int minusOne,
        int confused,
        int eyes,
        int heart,
        int hooray,
        int laugh,
        int rocket,
        int totalCount,
        String url) {
    public GitHubDiscussionReactions {
        requireNonNull(url, "url");
    }
}
