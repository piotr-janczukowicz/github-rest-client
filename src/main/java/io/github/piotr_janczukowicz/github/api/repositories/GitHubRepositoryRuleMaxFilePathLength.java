package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** max_file_path_length rule. */
@CatalogSchema("components/schemas/repository-rule-max-file-path-length")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleMaxFilePathLength(
        GitHubRepositoryRuleMaxFilePathLengthType type,
        @Nullable GitHubRepositoryRuleMaxFilePathLengthParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleMaxFilePathLength {
        requireNonNull(type, "type");
    }
}
