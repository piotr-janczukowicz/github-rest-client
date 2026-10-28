package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** commit_message_pattern rule with ruleset info. */
@CatalogSchema("components/schemas/repository-rule-detailed/oneOf/10")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCommitMessagePatternDetailed(
        GitHubRepositoryRuleCommitMessagePatternType type,
        @Nullable GitHubRepositoryRuleCommitMessagePatternParameters parameters,
        @Nullable GitHubRepositoryRuleRulesetInfoSourceType rulesetSourceType,
        @Nullable String rulesetSource,
        @Nullable Long rulesetId)
        implements GitHubRepositoryRuleDetailed {

    public GitHubRepositoryRuleCommitMessagePatternDetailed {
        requireNonNull(type, "type");
    }
}
