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
 * A summary representation of an automation including repository identity (used in cross-repository list responses).
 *
 * @param tools The tools available to the automation. Omitted when no tools are configured.
 * @param id The unique identifier of the automation.
 * @param repository Repository identity for an automation in cross-repository list responses.
 * @param name The name of the automation.
 * @param enabled Whether the automation is currently enabled.
 * @param type The type of automation. {@code personal} keeps it private to its creator and its runs act as that user.
 *     {@code shared} makes it belong to the repository, visible to anyone with read access to the repository, and its
 *     runs act as the Copilot app rather than as a person.
 * @param requireActorWritePermission When true, event-triggered runs require the event actor to have repo write
 *     permission, even when the org's allow_read_access_triggers policy is enabled. Does not apply to
 *     interval-scheduled runs, which always run as the automation owner.
 * @param model The model override for sessions triggered by this automation. Absent when the platform selects the
 *     model.
 * @param reasoningEffort The reasoning effort override for sessions triggered by this automation. Absent when the
 *     model's default is used.
 * @param triggers Trigger configuration for the automation.
 * @param createdAt When the automation was created.
 * @param updatedAt When the automation was last updated.
 * @param createdBy A GitHub user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-automations/200/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationsItem(
        @Nullable List<String> tools,
        String id,
        GitHubListAutomationsItemRepository repository,
        String name,
        @Nullable Boolean enabled,
        @Nullable GitHubListAutomationsItemType type,
        @Nullable Boolean requireActorWritePermission,
        @Nullable String model,
        @Nullable String reasoningEffort,
        @Nullable GitHubListAutomationsItemTriggers triggers,
        Instant createdAt,
        Instant updatedAt,
        GitHubListAutomationsItemCreatedBy createdBy) {
    public GitHubListAutomationsItem {
        requireNonNull(id, "id");
        requireNonNull(repository, "repository");
        requireNonNull(name, "name");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(createdBy, "createdBy");
    }
}
