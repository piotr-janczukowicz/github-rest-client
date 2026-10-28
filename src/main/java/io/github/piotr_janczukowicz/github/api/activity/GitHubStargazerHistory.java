package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Stargazer History.
 *
 * @param days The number of stars created on each day of the week, starting on Sunday
 * @param total The number of stars created during the week
 * @param week The start of the week, given as a Unix timestamp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/stargazer-history")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubStargazerHistory(List<Integer> days, int total, long week) {

    public GitHubStargazerHistory {
        requireNonNull(days, "days");
    }
}
