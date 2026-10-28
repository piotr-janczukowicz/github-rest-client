package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Generate a just-in-time configuration for a self-hosted runner of an enterprise.
 *
 * @param name The name of the new runner
 * @param runnerGroupId The ID of the runner group to register the runner to
 * @param labels The names of the custom labels to add to the runner
 * @param workFolder The working directory to be used for job execution, relative to the runner install directory
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/generate-runner-jitconfig-for-enterprise/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGenerateRunnerJitconfigForEnterprisePayload(
        String name,
        long runnerGroupId,
        List<String> labels,
        @Nullable String workFolder) {

    public GitHubGenerateRunnerJitconfigForEnterprisePayload {
        requireNonNull(name, "name");
        labels = List.copyOf(requireSizeAtMost(requireNonEmpty(labels, "labels"), 100, "labels"));
    }
}
