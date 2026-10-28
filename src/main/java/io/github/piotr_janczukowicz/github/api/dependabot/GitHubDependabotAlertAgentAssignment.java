package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Parameters for AI agent assignment. Only used when an agent bot login is included in {@code assignees}.
 *
 * @param customInstructions Custom instructions for the agent.
 * @param customAgent A custom agent identifier.
 * @param model The model to use for the agent.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/update-alert/requestBody/properties/agent_assignment")
public record GitHubDependabotAlertAgentAssignment(
        @Nullable String customInstructions,
        @Nullable String customAgent,
        @Nullable String model) {}
