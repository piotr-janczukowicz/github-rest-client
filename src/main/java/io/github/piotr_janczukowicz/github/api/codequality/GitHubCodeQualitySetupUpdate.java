package io.github.piotr_janczukowicz.github.api.codequality;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration for code quality setup.
 *
 * @param state The desired state of code quality setup.
 * @param runnerType Runner type to be used.
 * @param runnerLabel Runner label to be used if the runner type is labeled.
 * @param languages Languages to be analyzed.
 * @param aiFindingsOption Whether AI findings run for Code Quality on this repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-quality-setup-update")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeQualitySetupUpdate(
        @Nullable GitHubCodeQualitySetupUpdateState state,
        @Nullable GitHubCodeQualitySetupUpdateRunnerType runnerType,
        @Nullable String runnerLabel,
        @Nullable List<GitHubCodeQualitySetupUpdateLanguage> languages,
        @Nullable GitHubCodeQualitySetupUpdateAiFindingsOption aiFindingsOption) {
    public GitHubCodeQualitySetupUpdate {
        if (languages != null) {
            languages = List.copyOf(languages);
        }
    }
}
