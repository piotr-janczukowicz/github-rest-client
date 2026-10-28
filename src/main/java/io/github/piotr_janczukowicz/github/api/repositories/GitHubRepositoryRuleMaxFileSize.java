package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** max_file_size rule. */
@CatalogSchema("components/schemas/repository-rule-max-file-size")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleMaxFileSize(
        GitHubRepositoryRuleMaxFileSizeType type, @Nullable GitHubRepositoryRuleMaxFileSizeParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleMaxFileSize {
        requireNonNull(type, "type");
    }
}
