package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A page of tasks created by an automation.
 *
 * @param tasks Tasks created by the automation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-automation-tasks/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationTasksResponse(List<GitHubListAutomationTasksTask> tasks) {
    public GitHubListAutomationTasksResponse {
        requireNonNull(tasks, "tasks");
    }
}
