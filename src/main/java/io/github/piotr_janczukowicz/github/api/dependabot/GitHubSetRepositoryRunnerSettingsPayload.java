package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The runner Dependabot uses for this repository. {@code standard} accepts only {@code runner_type}.
 *
 * @param runnerType {@code labeled} for labeled runners, {@code standard} for standard GitHub-hosted runners.
 * @param runnerLabels Required for {@code labeled}. Exactly one non-blank runner label. Not allowed for
 *     {@code standard}.
 * @param runnerGroup Optional for {@code labeled}. Omitted, {@code null} or blank means no group. Not allowed for
 *     {@code standard}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/set-repository-runner-settings/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetRepositoryRunnerSettingsPayload(
        GitHubSetRepositoryRunnerSettingsRunnerType runnerType,
        @Nullable List<String> runnerLabels,
        @Nullable String runnerGroup) {
    public GitHubSetRepositoryRunnerSettingsPayload {
        requireNonNull(runnerType, "runnerType");
        if (runnerLabels != null) {
            runnerLabels = List.copyOf(requireSizeBetween(runnerLabels, 1, 1, "runnerLabels"));
        }
    }
}
