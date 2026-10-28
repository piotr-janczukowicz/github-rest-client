package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Custom hosted runner images of an enterprise.
 *
 * @param totalCount Total number of custom images
 * @param images The custom images
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-custom-images-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubActionsCustomImagesForEnterprise(
        int totalCount, List<GitHubActionsHostedRunnerCustomImage> images) {

    public GitHubActionsCustomImagesForEnterprise {
        requireNonNull(images, "images");
    }
}
