package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A secret scanning alert that is requested to be dismissed.
 *
 * @param secretType The type of secret that secret scanning detected
 * @param alertNumber The number of the secret scanning alert that was detected
 * @param reason The reason the user provided for requesting the dismissal
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/secret-scanning-dismissal-request/properties/data/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSecretScanningDismissalRequestData(
        @Nullable String secretType,
        @Nullable String alertNumber,
        @Nullable GitHubSecretScanningDismissalRequestReason reason) {}
