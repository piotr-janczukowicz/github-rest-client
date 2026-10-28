package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubOrgRule;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetBypassActor;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetTarget;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create organization repository ruleset ({@code repos/create-org-ruleset}).
 *
 * @param name Ruleset name
 * @param target Ruleset target
 * @param enforcement Enforcement level
 * @param bypassActors Bypass actors
 * @param conditions Organization ruleset conditions
 * @param rules Rules in the ruleset
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-org-ruleset/requestBody")
public record GitHubCreateOrgRulesetPayload(
        String name,
        @Nullable GitHubRepositoryRulesetTarget target,
        GitHubRepositoryRuleEnforcement enforcement,
        @Nullable List<GitHubRepositoryRulesetBypassActor> bypassActors,
        @Nullable GitHubOrgRulesetConditions conditions,
        @Nullable List<GitHubOrgRule> rules,
        @Nullable GitHubCreateOrgRulesetSourceType sourceType) {

    public GitHubCreateOrgRulesetPayload {
        requireNonNull(name, "name");
        requireNonNull(enforcement, "enforcement");
        if (conditions instanceof GitHubOrgRulesetConditions.Unknown) {
            throw new IllegalArgumentException("conditions must not be unknown conditions");
        }
        if (bypassActors != null) {
            bypassActors = List.copyOf(bypassActors);
        }
        if (rules != null) {
            rules = List.copyOf(rules);
        }
    }
}
