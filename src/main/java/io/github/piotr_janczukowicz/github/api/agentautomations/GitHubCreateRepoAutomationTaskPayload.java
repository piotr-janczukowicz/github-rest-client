package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request body for requesting an automation run.
 *
 * @param event The event to trigger. Only {@code manual} is supported.
 * @param context Additional context for this automation run, up to 10,000 characters. Supported only when {@code event}
 *     is {@code manual}. The context supplements the automation's configured instructions.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/create-repo-automation-task/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateRepoAutomationTaskPayload(
        GitHubCreateRepoAutomationTaskEvent event, @Nullable String context) {
    public GitHubCreateRepoAutomationTaskPayload {
        requireNonNull(event, "event");
        requireMaxLengthIfPresent(context, 10_000, "context");
    }
}
