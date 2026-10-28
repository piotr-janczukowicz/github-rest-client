package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Actions rule variants. */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = GitHubActionsRuleRestrictActionsActors.class, name = "restrict_actions_actors"),
    @JsonSubTypes.Type(value = GitHubActionsRuleRestrictActionEvents.class, name = "restrict_action_events")
})
@CatalogSchema("components/schemas/actions-rule")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubActionsRule
        permits GitHubActionsRuleRestrictActionsActors, GitHubActionsRuleRestrictActionEvents {}
