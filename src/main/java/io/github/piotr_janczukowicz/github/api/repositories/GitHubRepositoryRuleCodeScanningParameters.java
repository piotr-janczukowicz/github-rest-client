package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/** Parameters of the code scanning rule: the tools whose results must be available before a ref can be updated. */
@CatalogSchema("components/schemas/repository-rule-code-scanning/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleCodeScanningParameters(
        List<GitHubRepositoryRuleParamsCodeScanningTool> codeScanningTools) {
    public GitHubRepositoryRuleCodeScanningParameters {
        codeScanningTools = List.copyOf(requireNonNull(codeScanningTools, "codeScanningTools"));
    }
}
