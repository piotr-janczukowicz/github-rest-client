package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Labels of a self-hosted runner.
 *
 * @param totalCount Total number of labels
 * @param labels The labels of the runner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/actions/list-labels-for-self-hosted-runner-for-org/200",
    "operations/actions/add-custom-labels-to-self-hosted-runner-for-org/200",
    "operations/actions/set-custom-labels-for-self-hosted-runner-for-org/200",
    "operations/actions/remove-all-custom-labels-from-self-hosted-runner-for-org/200",
    "operations/actions/remove-custom-label-from-self-hosted-runner-for-org/200",
    "operations/actions/list-labels-for-self-hosted-runner-for-repo/200",
    "operations/actions/add-custom-labels-to-self-hosted-runner-for-repo/200",
    "operations/actions/set-custom-labels-for-self-hosted-runner-for-repo/200",
    "operations/actions/remove-all-custom-labels-from-self-hosted-runner-for-repo/200",
    "operations/actions/remove-custom-label-from-self-hosted-runner-for-repo/200"
})
public record GitHubRunnerLabels(int totalCount, List<GitHubRunnerLabel> labels) {

    public GitHubRunnerLabels {
        requireNonNull(labels, "labels");
    }
}
