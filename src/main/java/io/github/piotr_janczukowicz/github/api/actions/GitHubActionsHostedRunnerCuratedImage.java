package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Details of a curated hosted runner image.
 *
 * @param id The ID of the image
 * @param platform The operating system of the image
 * @param sizeGb Image size in GB
 * @param displayName Display name for this image
 * @param source The image provider
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-hosted-runner-curated-image")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunnerCuratedImage(
        String id, String platform, int sizeGb, String displayName, GitHubHostedRunnerImageSource source) {

    public GitHubActionsHostedRunnerCuratedImage {
        requireNonNull(id, "id");
        requireNonNull(platform, "platform");
        requireNonNull(displayName, "displayName");
        requireNonNull(source, "source");
    }
}
