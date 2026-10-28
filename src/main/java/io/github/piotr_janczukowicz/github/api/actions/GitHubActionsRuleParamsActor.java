package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An actor authorized to trigger Actions workflows.
 *
 * @param id ID of the actor authorized to trigger Actions workflows.
 * @param type The type of the actor
 */
@CatalogSchema("components/schemas/actions-rule-params-actor")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsRuleParamsActor(long id, GitHubActionsRuleParamsActorType type) {
    public GitHubActionsRuleParamsActor {
        requireNonNull(type, "type");
    }
}
