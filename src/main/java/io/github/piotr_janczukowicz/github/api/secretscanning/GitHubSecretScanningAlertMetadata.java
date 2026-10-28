package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Metadata key and value attached to a secret scanning alert.
 *
 * @param key metadata key
 * @param value metadata value
 */
@CatalogSchema("components/schemas/secret-scanning-alert-metadata/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSecretScanningAlertMetadata(String key, String value) {
    public GitHubSecretScanningAlertMetadata {
        requireNonNull(key, "key");
        requireNonNull(value, "value");
    }
}
