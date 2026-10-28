package io.github.piotr_janczukowicz.github.api.dependabot;

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
@CatalogSchema("components/schemas/dependabot-alert-dismissal-request/properties/requester")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubDependabotDismissalRequestRequester(
        @Nullable Long actorId, @Nullable String actorName) {}
