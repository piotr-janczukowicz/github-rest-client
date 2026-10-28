package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The repository a secret scanning dismissal request is for.
 *
 * @param id The ID of the repository the dismissal request is for
 * @param name The name of the repository the dismissal request is for
 * @param fullName The full name of the repository the dismissal request is for
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/secret-scanning-dismissal-request/properties/repository")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSecretScanningDismissalRequestRepository(
        @Nullable Long id, @Nullable String name, @Nullable String fullName) {}
