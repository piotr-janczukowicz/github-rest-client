package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Enterprise users whose Copilot seats to cancel.
 *
 * @param selectedUsernames Usernames of the enterprise members to unassign.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/cancel-copilot-seats-for-enterprise-users/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCancelCopilotSeatAssignmentForEnterpriseUsersPayload(List<String> selectedUsernames) {

    public GitHubCancelCopilotSeatAssignmentForEnterpriseUsersPayload {
        selectedUsernames = List.copyOf(requireNonEmpty(selectedUsernames, "selectedUsernames"));
    }
}
