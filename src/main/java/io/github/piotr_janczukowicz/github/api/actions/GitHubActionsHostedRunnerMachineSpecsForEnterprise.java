package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Machine specs available for GitHub-hosted runners of an enterprise.
 *
 * @param totalCount Total number of machine specs
 * @param machineSpecs The machine specs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/get-hosted-runners-machine-specs-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubActionsHostedRunnerMachineSpecsForEnterprise(
        int totalCount, List<GitHubActionsHostedRunnerMachineSpec> machineSpecs) {

    public GitHubActionsHostedRunnerMachineSpecsForEnterprise {
        requireNonNull(machineSpecs, "machineSpecs");
    }
}
