package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create repository ruleset ({@code repos/create-repo-ruleset}).
 *
 * @param name Ruleset name
 * @param target Ruleset target
 * @param enforcement Enforcement level
 * @param bypassActors Bypass actors
 * @param conditions Repository ruleset conditions
 * @param rules Rules in the ruleset
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-repo-ruleset/requestBody")
public record GitHubCreateRepoRulesetPayload(
        String name,
        @Nullable GitHubRepositoryRulesetPayloadTarget target,
        GitHubRepositoryRuleEnforcement enforcement,
        @Nullable List<GitHubRepositoryRulesetBypassActor> bypassActors,
        @Nullable GitHubRepositoryRulesetConditions conditions,
        @Nullable List<GitHubRepositoryRule> rules) {

    public GitHubCreateRepoRulesetPayload {
        requireNonNull(name, "name");
        requireNonNull(enforcement, "enforcement");
        if (bypassActors != null) {
            bypassActors = List.copyOf(bypassActors);
        }
        if (rules != null) {
            rules = List.copyOf(rules);
        }
    }
}
