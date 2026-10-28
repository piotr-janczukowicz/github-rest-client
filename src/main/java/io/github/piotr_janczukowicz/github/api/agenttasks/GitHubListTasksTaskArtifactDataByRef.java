package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Artifact reference identified by branch refs.
 *
 * @param headRef Head ref
 * @param baseRef Base ref
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-tasks/list-tasks/200/properties/tasks/items/properties/artifacts/items/properties/data/oneOf/1")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListTasksTaskArtifactDataByRef(String headRef, String baseRef)
        implements GitHubListTasksTaskArtifactData {
    public GitHubListTasksTaskArtifactDataByRef {
        requireNonNull(headRef, "headRef");
        requireNonNull(baseRef, "baseRef");
    }
}
