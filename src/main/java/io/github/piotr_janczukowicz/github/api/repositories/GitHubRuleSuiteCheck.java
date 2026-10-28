package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A status check associated with a required status checks rule evaluation.
 *
 * @param id unique identifier of the status check
 * @param context context name of the status check
 * @param state state of the status check
 * @param type type of the status check
 * @param app GitHub App associated with the status check
 */
@CatalogSchema("components/schemas/rule-suite-required-status-checks/properties/checks/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRuleSuiteCheck(
        @Nullable Long id,
        @Nullable String context,
        @Nullable String state,
        @Nullable String type,
        @Nullable GitHubRuleSuiteCheckApp app) {}
