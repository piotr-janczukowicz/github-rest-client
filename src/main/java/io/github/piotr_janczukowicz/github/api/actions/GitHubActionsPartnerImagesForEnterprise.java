package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Partner hosted runner images available to an enterprise.
 *
 * @param totalCount Total number of images
 * @param images The images
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/get-hosted-runners-partner-images-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubActionsPartnerImagesForEnterprise(
        int totalCount, List<GitHubActionsHostedRunnerCuratedImage> images) {

    public GitHubActionsPartnerImagesForEnterprise {
        requireNonNull(images, "images");
    }
}
