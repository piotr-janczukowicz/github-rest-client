package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Traffic data point.
 *
 * @param timestamp Timestamp
 * @param uniques Uniques
 * @param count Count
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/traffic")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubTraffic(Instant timestamp, long uniques, long count) {

    public GitHubTraffic {
        requireNonNull(timestamp, "timestamp");
    }
}
