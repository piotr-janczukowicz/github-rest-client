package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * GitHub-owned or partner hosted runner images.
 *
 * @param totalCount Total number of images
 * @param images The images
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/actions/get-hosted-runners-github-owned-images-for-org/200",
    "operations/actions/get-hosted-runners-partner-images-for-org/200"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsHostedRunnerImages(int totalCount, List<GitHubActionsHostedRunnerCuratedImage> images) {

    public GitHubActionsHostedRunnerImages {
        requireNonNull(images, "images");
    }
}
