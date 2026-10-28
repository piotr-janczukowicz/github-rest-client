package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** code_scanning rule. */
@CatalogSchema("components/schemas/repository-rule-code-scanning")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCodeScanning(
        GitHubRepositoryRuleCodeScanningType type, @Nullable GitHubRepositoryRuleCodeScanningParameters parameters)
        implements GitHubRepositoryRule, GitHubOrgRule {

    public GitHubRepositoryRuleCodeScanning {
        requireNonNull(type, "type");
    }
}
