package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Details of a custom hosted runner image.
 *
 * @param id The ID of the image
 * @param platform The operating system of the image
 * @param totalVersionsSize Total size of all the image versions in GB
 * @param name Display name for this image
 * @param source The image provider
 * @param versionsCount The number of image versions associated with the image
 * @param latestVersion The latest image version associated with the image
 * @param state The state of the image
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-hosted-runner-custom-image")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunnerCustomImage(
        long id,
        String platform,
        int totalVersionsSize,
        String name,
        String source,
        int versionsCount,
        String latestVersion,
        String state) {

    public GitHubActionsHostedRunnerCustomImage {
        requireNonNull(platform, "platform");
        requireNonNull(name, "name");
        requireNonNull(source, "source");
        requireNonNull(latestVersion, "latestVersion");
        requireNonNull(state, "state");
    }
}
