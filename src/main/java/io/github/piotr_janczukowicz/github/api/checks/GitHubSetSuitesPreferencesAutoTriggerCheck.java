package io.github.piotr_janczukowicz.github.api.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Automatic check suite creation setting for one GitHub App when setting preferences.
 *
 * @param appId The {@code id} of the GitHub App.
 * @param setting Set to {@code true} to enable automatic creation of CheckSuite events upon pushes to the repository,
 *     or {@code false} to disable it.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/set-suites-preferences/requestBody/properties/auto_trigger_checks/items")
public record GitHubSetSuitesPreferencesAutoTriggerCheck(long appId, boolean setting) {}
