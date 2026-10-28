package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List available codespace machines response.
 *
 * @param totalCount Total count
 * @param machines Machines
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/codespaces/repo-machines-for-authenticated-user/200",
    "operations/codespaces/codespace-machines-for-authenticated-user/200"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespaceMachinesPage(int totalCount, List<GitHubCodespaceMachine> machines) {

    public GitHubCodespaceMachinesPage {
        requireNonNull(machines, "machines");
    }
}
