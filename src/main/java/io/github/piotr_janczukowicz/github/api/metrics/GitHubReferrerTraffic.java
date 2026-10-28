package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Referrer traffic.
 *
 * @param referrer Referrer
 * @param count Count
 * @param uniques Uniques
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/referrer-traffic")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubReferrerTraffic(String referrer, long count, long uniques) {

    public GitHubReferrerTraffic {
        requireNonNull(referrer, "referrer");
    }
}
