package io.github.piotr_janczukowicz.github.api.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Preferences of a repository's check suites.
 *
 * @param autoTriggerChecks Enables or disables automatic creation of CheckSuite events upon pushes to the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/check-suite-preference/properties/preferences")
public record GitHubCheckSuitePreferences(@Nullable List<GitHubCheckSuiteAutoTriggerCheck> autoTriggerChecks) {}
