package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A summary representation of an automation (used in list responses).
 *
 * @param id The unique identifier of the automation.
 * @param name The name of the automation.
 * @param enabled Whether the automation is currently enabled.
 * @param ownerType Who owns the automation. {@code user} keeps it private to its creator and its runs act as that user.
 *     {@code repository} makes it belong to the repository, visible to anyone with read access to the repository, and
 *     its runs act as the Copilot app rather than as a person.
 * @param writeScope Controls which repository resources a run may modify. {@code trigger} limits writes to the resource
 *     that started the run, when one exists, and resources created during the run; {@code repository} allows writes to
 *     any resource in the repository. Supported values: {@code trigger}, {@code repository}.
 * @param requireActorWritePermission When true, event-triggered runs require the event actor to have repo write
 *     permission, even when the org's allow_read_access_triggers policy is enabled. Does not apply to
 *     interval-scheduled runs, which always run as the automation owner.
 * @param model The model to use for sessions triggered by this automation. Omit to use the platform default.
 * @param reasoningEffort The reasoning effort to use for sessions triggered by this automation. Omit or leave empty for
 *     no override. Currently supported values: {@code none}, {@code low}, {@code medium}, {@code high}, {@code xhigh},
 *     {@code max}
 * @param triggers Public trigger configuration keyed by stable public trigger identifier. Each trigger has a closed,
 *     trigger-specific configuration schema.
 * @param createdAt When the automation was created.
 * @param updatedAt When the automation was last updated.
 * @param createdBy A GitHub user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-repo-automations/200/properties/automations/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListRepoAutomationsAutomation(
        String id,
        String name,
        @Nullable Boolean enabled,
        @Nullable GitHubListRepoAutomationsAutomationOwnerType ownerType,
        @Nullable GitHubListRepoAutomationsAutomationWriteScope writeScope,
        @Nullable Boolean requireActorWritePermission,
        @Nullable String model,
        @Nullable String reasoningEffort,
        @Nullable GitHubListRepoAutomationsAutomationTriggers triggers,
        Instant createdAt,
        Instant updatedAt,
        GitHubListRepoAutomationsAutomationCreatedBy createdBy) {
    public GitHubListRepoAutomationsAutomation {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(createdBy, "createdBy");
    }
}
