package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Details of a hosted runner pool image.
 *
 * @param id The ID of the image
 * @param sizeGb Image size in GB
 * @param displayName Display name for this image
 * @param source The image provider
 * @param version The image version of the hosted runner pool
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-hosted-runner-pool-image")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunnerPoolImage(
        String id,
        int sizeGb,
        String displayName,
        GitHubHostedRunnerImageSource source,
        @Nullable String version) {

    public GitHubActionsHostedRunnerPoolImage {
        requireNonNull(id, "id");
        requireNonNull(displayName, "displayName");
        requireNonNull(source, "source");
    }
}
