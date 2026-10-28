package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Parameters of the restrict_actions_actors rule.
 *
 * @param allowedActors Select the actors who can run Actions workflows.
 */
@CatalogSchema("components/schemas/actions-rule-restrict-actions-actors/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsRuleRestrictActionsActorsParameters(List<GitHubActionsRuleParamsActor> allowedActors) {
    public GitHubActionsRuleRestrictActionsActorsParameters {
        allowedActors = List.copyOf(requireNonNull(allowedActors, "allowedActors"));
    }
}
