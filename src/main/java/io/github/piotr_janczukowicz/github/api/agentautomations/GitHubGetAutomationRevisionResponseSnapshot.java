package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The automation's public detail representation after the change, including its enabled state at configuration-save
 * time, not its current lifecycle state. Legacy deleted revisions are identified by {@code change_type}.
 *
 * @param id The unique identifier of the automation.
 * @param name The name of the automation.
 * @param enabled Whether the automation was enabled when this configuration was saved.
 * @param type The type of automation. {@code personal} keeps it private to its creator and its runs act as that user.
 *     {@code shared} makes it belong to the repository, visible to anyone with read access to the repository, and its
 *     runs act as the Copilot app rather than as a person.
 * @param requireActorWritePermission When true, event-triggered runs require the event actor to have repo write
 *     permission, even when the org's allow_read_access_triggers policy is enabled. Does not apply to
 *     interval-scheduled runs, which always run as the automation owner.
 * @param triggers Trigger configuration for the automation.
 * @param prompt The prompt for the automation.
 * @param tools The tools available to the automation.
 * @param model The model override for sessions triggered by this automation. Absent when the platform selects the
 *     model.
 * @param reasoningEffort The reasoning effort override for sessions triggered by this automation. Absent when the
 *     model's default is used.
 * @param createdAt When the automation was created.
 * @param updatedAt When the automation was last updated.
 * @param createdBy A GitHub user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/get-automation-revision/200/properties/snapshot")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetAutomationRevisionResponseSnapshot(
        String id,
        String name,
        @Nullable Boolean enabled,
        @Nullable GitHubAutomationType type,
        @Nullable Boolean requireActorWritePermission,
        @Nullable GitHubAutomationTriggers triggers,
        String prompt,
        @Nullable List<String> tools,
        @Nullable String model,
        @Nullable String reasoningEffort,
        Instant createdAt,
        Instant updatedAt,
        GitHubGetAutomationRevisionResponseSnapshotCreatedBy createdBy) {
    public GitHubGetAutomationRevisionResponseSnapshot {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
        requireNonNull(prompt, "prompt");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(createdBy, "createdBy");
    }
}
