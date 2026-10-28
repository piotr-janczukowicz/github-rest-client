package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Data describing the code scanning alert that is requested to be dismissed.
 *
 * @param reason The reason given for the dismissal request
 * @param alertNumber The number of the code scanning alert
 * @param prReviewThreadId The identifier of the pull request review thread the request is linked to
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-alert-dismissal-request/properties/data/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCodeScanningAlertDismissalRequestData(
        @Nullable String reason,
        @Nullable String alertNumber,
        @Nullable String prReviewThreadId) {}
