package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Add custom labels to a self-hosted runner of a repository.
 *
 * @param labels The names of the custom labels to add to the runner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/add-custom-labels-to-self-hosted-runner-for-repo/requestBody")
public record GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload(List<String> labels) {

    public GitHubAddCustomLabelsToSelfHostedRunnerForRepoPayload {
        labels = List.copyOf(requireSizeAtMost(requireNonEmpty(labels, "labels"), 100, "labels"));
    }
}
