package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The user who reviewed a bypass request.
 *
 * @param actorId The ID of the GitHub user who reviewed the bypass request
 * @param actorName The name of the GitHub user who reviewed the bypass request
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/bypass-response/properties/reviewer")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubBypassResponseReviewer(
        @Nullable Long actorId, @Nullable String actorName) {}
