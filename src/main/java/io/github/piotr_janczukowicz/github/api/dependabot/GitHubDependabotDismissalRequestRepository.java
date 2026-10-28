package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The repository a dismissal request is for.
 *
 * @param id The unique identifier of the repository
 * @param name The name of the repository
 * @param fullName The full name of the repository, including the owner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-dismissal-request/properties/repository")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubDependabotDismissalRequestRepository(
        @Nullable Long id, @Nullable String name, @Nullable String fullName) {}
