package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Labels of a self-hosted runner of an enterprise.
 *
 * @param totalCount Total number of labels
 * @param labels The labels of the runner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/enterprise-admin/list-labels-for-self-hosted-runner-for-enterprise/200",
    "operations/enterprise-admin/add-custom-labels-to-self-hosted-runner-for-enterprise/200",
    "operations/enterprise-admin/set-custom-labels-for-self-hosted-runner-for-enterprise/200",
    "operations/enterprise-admin/remove-all-custom-labels-from-self-hosted-runner-for-enterprise/200",
    "operations/enterprise-admin/remove-custom-label-from-self-hosted-runner-for-enterprise/200"
})
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubRunnerLabelsForEnterprise(int totalCount, List<GitHubRunnerLabel> labels) {

    public GitHubRunnerLabelsForEnterprise {
        requireNonNull(labels, "labels");
    }
}
