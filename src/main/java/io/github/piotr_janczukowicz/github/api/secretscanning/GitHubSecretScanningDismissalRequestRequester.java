package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The user who requested a secret scanning dismissal.
 *
 * @param actorId The ID of the GitHub user who requested the dismissal
 * @param actorName The name of the GitHub user who requested the dismissal
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/secret-scanning-dismissal-request/properties/requester")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSecretScanningDismissalRequestRequester(
        @Nullable Long actorId, @Nullable String actorName) {}
