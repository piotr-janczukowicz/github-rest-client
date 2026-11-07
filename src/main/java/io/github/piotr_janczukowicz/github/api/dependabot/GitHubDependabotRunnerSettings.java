package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The runner Dependabot uses for this repository, as shown in the repository settings.
 *
 * @param runnerType {@code labeled} when Dependabot runs on labeled runners. Otherwise {@code standard}.
 * @param runnerLabels Runner labels Dependabot uses. Empty for {@code standard}.
 * @param runnerGroup Runner group Dependabot uses, or {@code null} for no group.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-runner-settings")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDependabotRunnerSettings(
        GitHubDependabotRunnerSettingsRunnerType runnerType,
        List<String> runnerLabels,
        @Nullable String runnerGroup) {
    public GitHubDependabotRunnerSettings {
        requireNonNull(runnerType, "runnerType");
        requireNonNull(runnerLabels, "runnerLabels");
    }
}
