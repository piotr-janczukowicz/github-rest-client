package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A Git branch reference
 *
 * @param headRef Head branch name
 * @param baseRef Base branch name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-automation-tasks/200/properties/tasks/items/properties/artifacts/items/properties/data/oneOf/1")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationTasksTaskArtifactDataByRef(String headRef, String baseRef)
        implements GitHubListAutomationTasksTaskArtifactData {
    public GitHubListAutomationTasksTaskArtifactDataByRef {
        requireNonNull(headRef, "headRef");
        requireNonNull(baseRef, "baseRef");
    }
}
