package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Details of a hosted runner custom image version.
 *
 * @param version The version of the image
 * @param state The state of the image version
 * @param sizeGb Image version size in GB
 * @param createdOn The creation date time of the image version
 * @param stateDetails The image version status details
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-hosted-runner-custom-image-version")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunnerCustomImageVersion(
        String version, String state, int sizeGb, String createdOn, String stateDetails) {

    public GitHubActionsHostedRunnerCustomImageVersion {
        requireNonNull(version, "version");
        requireNonNull(state, "state");
        requireNonNull(createdOn, "createdOn");
        requireNonNull(stateDetails, "stateDetails");
    }
}
