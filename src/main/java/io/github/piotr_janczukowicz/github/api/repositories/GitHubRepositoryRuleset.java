package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Repository ruleset. */
@CatalogSchema("components/schemas/repository-ruleset")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleset(
        long id,
        String name,
        @Nullable GitHubRepositoryRulesetTarget target,
        @Nullable GitHubRulesetSourceType sourceType,
        String source,
        GitHubRepositoryRuleEnforcement enforcement,
        @Nullable List<GitHubRepositoryRulesetBypassActor> bypassActors,
        @Nullable GitHubRepositoryRulesetCurrentUserCanBypass currentUserCanBypass,
        @Nullable String nodeId,
        @JsonProperty("_links") @Nullable GitHubRepositoryRulesetLinks links,
        @Nullable GitHubRepositoryRulesetAnyOfConditions conditions,
        @Nullable List<GitHubRepositoryRule> rules,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {

    public GitHubRepositoryRuleset {
        requireNonNull(name, "name");
        requireNonNull(source, "source");
        requireNonNull(enforcement, "enforcement");
    }
}
