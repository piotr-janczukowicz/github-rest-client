package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Runs on the default branch when a security alert of this kind is created in the repository. These are system events
 * without an issue, pull request, or human actor, and cannot be run manually.
 *
 * @param writeScope Sets the write scope for this trigger's runs. Omit to inherit the automation's effective scope
 *     (default {@code trigger}). Organization or repository policy may restrict the effective scope to {@code trigger}.
 *     Explicitly setting {@code repository} on create or edit returns 422 when policy validation is enabled and that
 *     policy applies.
 * @param allowCopilotTriggers When true, events Copilot creates, including from other automations and Copilot sessions,
 *     can start this automation through this trigger. Defaults to false. The Copilot coding agent app cannot turn it
 *     on: creating a trigger with true, or changing it from false to true, returns 422.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/secret_scanning_alert")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetAutomationRevisionResponseSnapshotTriggersSecretScanningAlert(
        @Nullable GitHubAutomationWriteScope writeScope,
        @Nullable Boolean allowCopilotTriggers) {}
