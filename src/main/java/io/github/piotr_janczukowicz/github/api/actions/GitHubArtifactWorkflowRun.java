package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Workflow run that produced an artifact.
 *
 * @param id The ID of the workflow run
 * @param repositoryId The ID of the repository of the workflow run
 * @param headRepositoryId The ID of the head repository of the workflow run
 * @param headBranch The head branch of the workflow run
 * @param headSha The head SHA of the workflow run
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/artifact/properties/workflow_run")
public record GitHubArtifactWorkflowRun(
        @Nullable Long id,
        @Nullable Long repositoryId,
        @Nullable Long headRepositoryId,
        @Nullable String headBranch,
        @Nullable String headSha) {}
