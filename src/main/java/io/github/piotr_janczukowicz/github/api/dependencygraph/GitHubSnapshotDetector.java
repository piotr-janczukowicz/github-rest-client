package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A description of the detector used.
 *
 * @param name The name of the detector used.
 * @param version The version of the detector used.
 * @param url The url of the detector used.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/snapshot/properties/detector")
@RecordBuilder
public record GitHubSnapshotDetector(String name, String version, String url) {

    public GitHubSnapshotDetector {
        requireNonNull(name, "name");
        requireNonNull(version, "version");
        requireNonNull(url, "url");
    }
}
