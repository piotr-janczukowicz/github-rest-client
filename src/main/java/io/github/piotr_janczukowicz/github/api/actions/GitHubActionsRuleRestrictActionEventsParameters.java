package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Parameters of the restrict_action_events rule.
 *
 * @param allowedEvents Select the events that can trigger Actions workflows.
 */
@CatalogSchema("components/schemas/actions-rule-restrict-action-events/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsRuleRestrictActionEventsParameters(
        List<GitHubActionsRuleRestrictActionEventsAllowedEvent> allowedEvents) {
    public GitHubActionsRuleRestrictActionEventsParameters {
        allowedEvents = List.copyOf(requireNonNull(allowedEvents, "allowedEvents"));
    }
}
