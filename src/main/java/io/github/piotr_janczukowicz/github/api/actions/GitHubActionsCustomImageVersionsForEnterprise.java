package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Versions of a custom hosted runner image of an enterprise.
 *
 * @param totalCount Total number of image versions
 * @param imageVersions The image versions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-custom-image-versions-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubActionsCustomImageVersionsForEnterprise(
        int totalCount, List<GitHubActionsHostedRunnerCustomImageVersion> imageVersions) {

    public GitHubActionsCustomImageVersionsForEnterprise {
        requireNonNull(imageVersions, "imageVersions");
    }
}
