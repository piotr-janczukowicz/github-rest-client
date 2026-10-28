package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A secret that secret scanning detected and that is requested to be bypassed.
 *
 * @param secretType The type of secret that secret scanning detected
 * @param bypassReason The reason the bypass was requested
 * @param path The path in the repo where the secret was located during the request
 * @param branch The branch in the repo where the secret was located during the request
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/secret-scanning-bypass-request/properties/data/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSecretScanningBypassRequestData(
        @Nullable String secretType,
        @Nullable GitHubSecretScanningBypassRequestBypassReason bypassReason,
        @Nullable String path,
        @Nullable String branch) {}
