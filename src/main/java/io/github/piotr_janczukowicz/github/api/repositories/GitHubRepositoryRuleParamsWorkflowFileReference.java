package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** WorkflowFileReference. */
@CatalogSchema("components/schemas/repository-rule-params-workflow-file-reference")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleParamsWorkflowFileReference(
        String path,
        @Nullable String ref,
        long repositoryId,
        @Nullable String sha) {
    public GitHubRepositoryRuleParamsWorkflowFileReference {
        requireNonNull(path, "path");
    }
}
