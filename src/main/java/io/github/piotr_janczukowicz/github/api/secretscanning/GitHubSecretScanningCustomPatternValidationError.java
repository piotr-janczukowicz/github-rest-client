package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Secret Scanning Custom Pattern Validation Error. A validation error for a custom pattern in a batch operation.
 *
 * @param code A machine-readable code describing the error.
 * @param message A human-readable description of the error.
 */
@CatalogSchema("components/schemas/secret-scanning-custom-pattern-validation-error")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSecretScanningCustomPatternValidationError(
        @Nullable GitHubSecretScanningCustomPatternValidationErrorCode code,
        @Nullable String message) {}
