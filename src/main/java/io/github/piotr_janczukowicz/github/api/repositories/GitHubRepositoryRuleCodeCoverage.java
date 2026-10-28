package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** code_coverage rule. */
@CatalogSchema("components/schemas/repository-rule-code-coverage")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryRuleCodeCoverage(
        GitHubRepositoryRuleCodeCoverageType type, @Nullable GitHubRepositoryRuleCodeCoverageParameters parameters)
        implements GitHubRepositoryRule {

    public GitHubRepositoryRuleCodeCoverage {
        requireNonNull(type, "type");
    }
}
