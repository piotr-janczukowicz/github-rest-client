package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Add users to the codespaces access list of an organization.
 *
 * @param selectedUsernames The usernames of the organization members and outside collaborators who should have access
 *     to codespaces in the organization. The provided list of usernames is added to any existing value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/set-codespaces-access-users/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetCodespacesAccessUsersPayload(List<String> selectedUsernames) {

    public GitHubSetCodespacesAccessUsersPayload {
        selectedUsernames = List.copyOf(
                requireSizeAtMost(requireNonNull(selectedUsernames, "selectedUsernames"), 100, "selectedUsernames"));
    }
}
