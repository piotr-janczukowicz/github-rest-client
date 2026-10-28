package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Machine spec of a hosted runner.
 *
 * @param id The ID used for the size parameter when creating a new runner
 * @param cpuCores The number of cores
 * @param memoryGb The available RAM for the machine spec
 * @param storageGb The available SSD storage for the machine spec
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-hosted-runner-machine-spec")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunnerMachineSpec(String id, int cpuCores, int memoryGb, int storageGb) {

    public GitHubActionsHostedRunnerMachineSpec {
        requireNonNull(id, "id");
    }
}
