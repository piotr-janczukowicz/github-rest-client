package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** required_linear_history rule with ruleset info. */
@CatalogSchema("components/schemas/repository-rule-detailed/oneOf/3")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleRequiredLinearHistoryDetailed(
        GitHubRepositoryRuleRequiredLinearHistoryType type,
        @Nullable GitHubRepositoryRuleRulesetInfoSourceType rulesetSourceType,
        @Nullable String rulesetSource,
        @Nullable Long rulesetId)
        implements GitHubRepositoryRuleDetailed {

    public GitHubRepositoryRuleRequiredLinearHistoryDetailed {
        requireNonNull(type, "type");
    }
}
