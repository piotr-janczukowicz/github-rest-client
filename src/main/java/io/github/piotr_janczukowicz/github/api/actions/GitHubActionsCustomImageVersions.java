package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Versions of a custom hosted runner image.
 *
 * @param totalCount Total number of image versions
 * @param imageVersions The image versions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-custom-image-versions-for-org/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsCustomImageVersions(
        int totalCount, List<GitHubActionsHostedRunnerCustomImageVersion> imageVersions) {

    public GitHubActionsCustomImageVersions {
        requireNonNull(imageVersions, "imageVersions");
    }
}
