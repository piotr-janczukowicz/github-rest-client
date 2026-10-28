package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Resources attached to a Copilot Space.
 *
 * @param resources The list of resources attached to this Copilot Space.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/copilot-spaces/list-resources-for-org/200",
    "operations/copilot-spaces/list-resources-for-user/200"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceResourcesPage(List<GitHubCopilotSpaceResource> resources) {

    public GitHubCopilotSpaceResourcesPage {
        requireNonNull(resources, "resources");
    }
}
