package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List codespaces response.
 *
 * @param totalCount Total count
 * @param codespaces Codespaces
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/codespaces/list-in-organization/200",
    "operations/codespaces/get-codespaces-for-user-in-org/200",
    "operations/codespaces/list-in-repository-for-authenticated-user/200",
    "operations/codespaces/list-for-authenticated-user/200"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespacesPage(int totalCount, List<GitHubCodespace> codespaces) {

    public GitHubCodespacesPage {
        requireNonNull(codespaces, "codespaces");
    }
}
