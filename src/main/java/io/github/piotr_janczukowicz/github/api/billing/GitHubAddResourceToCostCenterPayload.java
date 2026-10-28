package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for adding resources to a cost center. At least one list is required.
 *
 * @param users The usernames of the users to add to the cost center.
 * @param organizations The organizations to add to the cost center.
 * @param repositories The repositories to add to the cost center.
 * @param enterpriseTeams The enterprise teams to add to the cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/add-resource-to-cost-center/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubAddResourceToCostCenterPayload(
        @Nullable List<String> users,
        @Nullable List<String> organizations,
        @Nullable List<String> repositories,
        @Nullable List<String> enterpriseTeams) {
    public GitHubAddResourceToCostCenterPayload {
        requireAtLeastOne(
                users != null ? users : organizations,
                repositories != null ? repositories : enterpriseTeams,
                "users, organizations, repositories or enterpriseTeams");
        if (users != null) {
            users = List.copyOf(users);
        }
        if (organizations != null) {
            organizations = List.copyOf(organizations);
        }
        if (repositories != null) {
            repositories = List.copyOf(repositories);
        }
        if (enterpriseTeams != null) {
            enterpriseTeams = List.copyOf(enterpriseTeams);
        }
    }
}
