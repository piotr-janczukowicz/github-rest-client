package io.github.piotr_janczukowicz.github.api.codespaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update a codespace for the authenticated user.
 *
 * @param machine A valid machine to transition this codespace to.
 * @param displayName Display name for this codespace.
 * @param recentFolders Recently opened folders inside the codespace.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/update-for-authenticated-user/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateForAuthenticatedUserPayload(
        @Nullable String machine,
        @Nullable String displayName,
        @Nullable List<String> recentFolders) {

    public GitHubUpdateForAuthenticatedUserPayload {
        if (recentFolders != null) {
            recentFolders = List.copyOf(recentFolders);
        }
    }
}
