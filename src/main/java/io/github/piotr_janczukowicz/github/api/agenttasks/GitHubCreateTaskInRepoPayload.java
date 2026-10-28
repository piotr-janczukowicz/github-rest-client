package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create an agent task in a repository.
 *
 * @param prompt The user's prompt for the agent
 * @param model The model to use for this task. The allowed models may change over time and depend on the user's GitHub
 *     Copilot plan and organization policies. Currently supported values: {@code claude-sonnet-4.6},
 *     {@code claude-opus-4.6}, {@code gpt-5.2-codex}, {@code gpt-5.3-codex}, {@code gpt-5.4},
 *     {@code claude-sonnet-4.5}, {@code claude-opus-4.5}
 * @param customAgent Optional identifier for a custom agent to use for this task. Use the custom agent's filename
 *     without the extension - for example, for a {@code .github/agents/performance-optimizer.agent.md} custom agent,
 *     use {@code performance-optimizer}.
 * @param createPullRequest Whether to create a PR.
 * @param baseRef Base ref for new branch/PR
 * @param headRef Head ref for existing branch/PR. If provided with {@code base_ref}, the agent looks up open PR context
 *     for {@code head_ref} targeting {@code base_ref} and commits to {@code head_ref} instead of creating a new branch.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/create-task-in-repo/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateTaskInRepoPayload(
        String prompt,
        @Nullable String model,
        @Nullable String customAgent,
        @Nullable Boolean createPullRequest,
        @Nullable String baseRef,
        @Nullable String headRef) {
    public GitHubCreateTaskInRepoPayload {
        requireNonNull(prompt, "prompt");
    }
}
