package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The user who requested a dismissal.
 *
 * @param actorId The unique identifier of the user
 * @param actorName The login name of the user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-alert-dismissal-request/properties/requester")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCodeScanningAlertDismissalRequestRequester(
        @Nullable Long actorId, @Nullable String actorName) {}
