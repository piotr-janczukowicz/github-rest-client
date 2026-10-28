package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** code_quality rule with ruleset info. */
@CatalogSchema("components/schemas/repository-rule-detailed/oneOf/17")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCodeQualityDetailed(
        GitHubRepositoryRuleCodeQualityType type,
        @Nullable GitHubRepositoryRuleCodeQualityParameters parameters,
        @Nullable GitHubRepositoryRuleRulesetInfoSourceType rulesetSourceType,
        @Nullable String rulesetSource,
        @Nullable Long rulesetId)
        implements GitHubRepositoryRuleDetailed {

    public GitHubRepositoryRuleCodeQualityDetailed {
        requireNonNull(type, "type");
    }
}
