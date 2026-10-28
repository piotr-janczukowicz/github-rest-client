package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A GitHub resource (pull request, issue, etc.)
 *
 * @param id GitHub resource ID
 * @param globalId GraphQL global ID
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-automation-tasks/200/properties/tasks/items/properties/artifacts/items/properties/data/oneOf/0")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationTasksTaskArtifactDataById(
        long id, @Nullable String globalId) implements GitHubListAutomationTasksTaskArtifactData {}
