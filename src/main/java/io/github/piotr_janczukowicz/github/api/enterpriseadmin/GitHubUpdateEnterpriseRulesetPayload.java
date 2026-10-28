package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRule;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetBypassActor;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Enterprise ruleset fields to update. Every field is optional; an absent field keeps its current value.
 *
 * @param name The name of the ruleset
 * @param target The target of the ruleset
 * @param enforcement The enforcement level of the ruleset
 * @param bypassActors The actors that can bypass the rules in this ruleset
 * @param conditions Conditions for the enterprise ruleset
 * @param rules The rules to apply to the enterprise ruleset
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-enterprise-ruleset/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseRulesetPayload(
        @Nullable String name,
        @Nullable GitHubUpdateEnterpriseRulesetTarget target,
        @Nullable GitHubRepositoryRuleEnforcement enforcement,
        @Nullable List<GitHubRepositoryRulesetBypassActor> bypassActors,
        @Nullable GitHubEnterpriseRulesetConditions conditions,
        @Nullable List<GitHubRepositoryRule> rules) {

    public GitHubUpdateEnterpriseRulesetPayload {
        if (bypassActors != null) {
            bypassActors = List.copyOf(bypassActors);
        }
        if (rules != null) {
            rules = List.copyOf(rules);
        }
    }
}
