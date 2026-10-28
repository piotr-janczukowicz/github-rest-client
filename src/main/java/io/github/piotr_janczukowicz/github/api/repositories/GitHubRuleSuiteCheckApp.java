package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The GitHub App associated with a status check.
 *
 * @param id unique identifier of the GitHub App
 * @param slug slug of the GitHub App
 * @param name name of the GitHub App
 */
@CatalogSchema("components/schemas/rule-suite-required-status-checks/properties/checks/items/properties/app")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRuleSuiteCheckApp(
        @Nullable Long id, @Nullable String slug, @Nullable String name) {}
