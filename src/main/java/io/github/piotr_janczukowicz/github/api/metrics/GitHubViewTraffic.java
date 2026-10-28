package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * View traffic.
 *
 * @param count Count
 * @param uniques Uniques
 * @param views Views
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/view-traffic")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubViewTraffic(long count, long uniques, List<GitHubTraffic> views) {

    public GitHubViewTraffic {
        requireNonNull(views, "views");
    }
}
