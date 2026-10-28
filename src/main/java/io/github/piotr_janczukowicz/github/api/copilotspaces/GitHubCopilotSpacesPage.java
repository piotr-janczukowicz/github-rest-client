package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * One page of Copilot Spaces.
 *
 * @param spaces The list of Copilot Spaces on this page of results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"operations/copilot-spaces/list-for-org/200", "operations/copilot-spaces/list-for-user/200"})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpacesPage(List<GitHubCopilotSpace> spaces) {

    public GitHubCopilotSpacesPage {
        requireNonNull(spaces, "spaces");
    }
}
