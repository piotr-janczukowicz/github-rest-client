package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Choose specific GitHub events that will trigger Actions workflows.
 *
 * @param type The rule type
 * @param parameters The rule parameters
 */
@CatalogSchema("components/schemas/actions-rule-restrict-action-events")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsRuleRestrictActionEvents(
        GitHubActionsRuleRestrictActionEventsType type,
        @Nullable GitHubActionsRuleRestrictActionEventsParameters parameters)
        implements GitHubActionsRule {

    public GitHubActionsRuleRestrictActionEvents {
        requireNonNull(type, "type");
    }
}
