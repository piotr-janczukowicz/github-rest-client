package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Enterprise users to add to the Copilot subscription.
 *
 * @param selectedUsernames Usernames of the enterprise members to grant Copilot seats.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/add-copilot-seats-for-enterprise-users/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubAddCopilotSeatsForEnterpriseUsersPayload(List<String> selectedUsernames) {

    public GitHubAddCopilotSeatsForEnterpriseUsersPayload {
        selectedUsernames = List.copyOf(requireNonEmpty(selectedUsernames, "selectedUsernames"));
    }
}
