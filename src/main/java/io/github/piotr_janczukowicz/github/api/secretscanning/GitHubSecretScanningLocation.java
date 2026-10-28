package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Represents a secret scanning location.
 *
 * @param type The location type. Because secrets may be found in different types of resources (ie. code, comments,
 *     issues, pull requests, discussions), this field identifies the type of resource where the secret was found.
 * @param details Details
 */
@CatalogSchema("components/schemas/secret-scanning-location")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocation(
        @Nullable GitHubSecretScanningLocationType type,
        @Nullable GitHubSecretScanningLocationDetails details) {}
