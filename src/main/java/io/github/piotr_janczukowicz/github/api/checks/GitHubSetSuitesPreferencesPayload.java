package io.github.piotr_janczukowicz.github.api.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for {@code PATCH /repos/{owner}/{repo}/check-suites/preferences}.
 *
 * @param autoTriggerChecks Enables or disables automatic creation of CheckSuite events upon pushes to the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/set-suites-preferences/requestBody")
public record GitHubSetSuitesPreferencesPayload(
        @Nullable List<GitHubSetSuitesPreferencesAutoTriggerCheck> autoTriggerChecks) {

    public GitHubSetSuitesPreferencesPayload {
        if (autoTriggerChecks != null) {
            autoTriggerChecks = List.copyOf(autoTriggerChecks);
        }
    }
}
