package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Replace the custom labels of a self-hosted runner of a repository.
 *
 * @param labels The names of the custom labels to set for the runner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-custom-labels-for-self-hosted-runner-for-repo/requestBody")
public record GitHubSetCustomLabelsForSelfHostedRunnerForRepoPayload(List<String> labels) {

    public GitHubSetCustomLabelsForSelfHostedRunnerForRepoPayload {
        labels = List.copyOf(requireSizeAtMost(requireNonNull(labels, "labels"), 100, "labels"));
    }
}
