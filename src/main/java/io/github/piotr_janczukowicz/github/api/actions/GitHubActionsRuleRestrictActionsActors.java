package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Choose specific actors that are authorized to trigger Actions workflows.
 *
 * @param type The rule type
 * @param parameters The rule parameters
 */
@CatalogSchema("components/schemas/actions-rule-restrict-actions-actors")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsRuleRestrictActionsActors(
        GitHubActionsRuleRestrictActionsActorsType type,
        @Nullable GitHubActionsRuleRestrictActionsActorsParameters parameters)
        implements GitHubActionsRule {

    public GitHubActionsRuleRestrictActionsActors {
        requireNonNull(type, "type");
    }
}
