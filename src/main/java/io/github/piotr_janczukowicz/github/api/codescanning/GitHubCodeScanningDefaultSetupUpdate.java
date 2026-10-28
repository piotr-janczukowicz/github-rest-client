package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration for code scanning default setup.
 *
 * @param state The desired state of code scanning default setup.
 * @param runnerType Runner type to be used.
 * @param runnerLabel Runner label to be used if the runner type is labeled.
 * @param querySuite CodeQL query suite to be used.
 * @param threatModel Threat model to be used for code scanning analysis.
 * @param languages CodeQL languages to be analyzed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-default-setup-update")
public record GitHubCodeScanningDefaultSetupUpdate(
        @Nullable GitHubCodeScanningDefaultSetupUpdateState state,
        @Nullable GitHubCodeScanningDefaultSetupUpdateRunnerType runnerType,
        @Nullable String runnerLabel,
        @Nullable GitHubCodeScanningDefaultSetupUpdateQuerySuite querySuite,
        @Nullable GitHubCodeScanningDefaultSetupUpdateThreatModel threatModel,
        @Nullable List<GitHubCodeScanningDefaultSetupUpdateLanguage> languages) {
    public GitHubCodeScanningDefaultSetupUpdate {
        if (languages != null) {
            languages = List.copyOf(languages);
        }
    }
}
