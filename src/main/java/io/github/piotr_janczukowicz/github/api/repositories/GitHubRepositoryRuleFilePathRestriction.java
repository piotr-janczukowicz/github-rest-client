package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** file_path_restriction rule. */
@CatalogSchema("components/schemas/repository-rule-file-path-restriction")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleFilePathRestriction(
        GitHubRepositoryRuleFilePathRestrictionType type,
        @Nullable GitHubRepositoryRuleFilePathRestrictionParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleFilePathRestriction {
        requireNonNull(type, "type");
    }
}
