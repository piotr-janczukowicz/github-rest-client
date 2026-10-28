package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Set which users can access codespaces in an organization.
 *
 * @param visibility Which users can access codespaces in the organization. {@code disabled} means that no users can
 *     access codespaces in the organization.
 * @param selectedUsernames The usernames of the organization members who should have access to codespaces in the
 *     organization. Required when {@code visibility} is {@code selected_members}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/set-codespaces-access/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetCodespacesAccessPayload(
        GitHubCodespacesAccessVisibility visibility,
        @Nullable List<String> selectedUsernames) {

    public GitHubSetCodespacesAccessPayload {
        requireNonNull(visibility, "visibility");
        if (selectedUsernames != null) {
            selectedUsernames = List.copyOf(requireSizeAtMost(selectedUsernames, 100, "selectedUsernames"));
        }
    }
}
