package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** file_extension_restriction rule. */
@CatalogSchema("components/schemas/repository-rule-file-extension-restriction")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleFileExtensionRestriction(
        GitHubRepositoryRuleFileExtensionRestrictionType type,
        @Nullable GitHubRepositoryRuleFileExtensionRestrictionParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleFileExtensionRestriction {
        requireNonNull(type, "type");
    }
}
