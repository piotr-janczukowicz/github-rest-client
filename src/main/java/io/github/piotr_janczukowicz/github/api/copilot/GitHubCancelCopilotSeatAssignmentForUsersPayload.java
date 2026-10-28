package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Users to remove from the Copilot subscription for an organization.
 *
 * @param selectedUsernames The usernames of the organization members for which to revoke access to GitHub Copilot.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/cancel-copilot-seat-assignment-for-users/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCancelCopilotSeatAssignmentForUsersPayload(List<String> selectedUsernames) {

    public GitHubCancelCopilotSeatAssignmentForUsersPayload {
        selectedUsernames = List.copyOf(requireNonEmpty(selectedUsernames, "selectedUsernames"));
    }
}
