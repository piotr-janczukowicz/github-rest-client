package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Data describing the Dependabot alert that is requested to be dismissed.
 *
 * @param reason The reason given for the dismissal request
 * @param alertNumber The number of the Dependabot alert
 * @param alertTitle The title of the Dependabot alert
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-dismissal-request/properties/data/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubDependabotDismissalRequestData(
        @Nullable String reason,
        @Nullable String alertNumber,
        @Nullable String alertTitle) {}
