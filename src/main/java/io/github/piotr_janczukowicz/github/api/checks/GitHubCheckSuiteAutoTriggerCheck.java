package io.github.piotr_janczukowicz.github.api.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Automatic check suite creation setting for one GitHub App.
 *
 * @param appId The {@code id} of the GitHub App.
 * @param setting Whether automatic creation of CheckSuite events upon pushes is enabled for the app.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/check-suite-preference/properties/preferences/properties/auto_trigger_checks/items")
public record GitHubCheckSuiteAutoTriggerCheck(long appId, boolean setting) {}
