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
 * An immutable snapshot of an automation's public definition after one configuration save, with the attribution needed
 * to understand who made the change. Legacy history may also contain lifecycle-only snapshots.
 *
 * @param id The unique identifier of this revision.
 * @param automationId The automation this revision belongs to.
 * @param revisionNumber Position of this revision in the automation's history, starting at 1.
 * @param changeType The kind of change recorded by the revision.
 * @param creator The automation's original creator. Omitted when not recorded.
 * @param editor The person who made this change. Omitted for system changes and legacy records.
 * @param editorType The kind of actor that made the change.
 * @param changedAt When the change was applied.
 * @param restoredFromRevisionId The revision a rollback restored. Present only when {@code change_type} is
 *     {@code rolled_back}.
 * @param snapshot The automation's public detail representation after the change, including its enabled state at
 *     configuration-save time, not its current lifecycle state. Legacy deleted revisions are identified by
 *     {@code change_type}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-automation-revisions/200/properties/revisions/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationRevisionsRevision(
        String id,
        String automationId,
        long revisionNumber,
        GitHubAutomationRevisionChangeType changeType,
        @Nullable GitHubListAutomationRevisionsRevisionCreator creator,
        @Nullable GitHubListAutomationRevisionsRevisionEditor editor,
        @Nullable GitHubAutomationRevisionEditorType editorType,
        Instant changedAt,
        @Nullable String restoredFromRevisionId,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshot snapshot) {
    public GitHubListAutomationRevisionsRevision {
        requireNonNull(id, "id");
        requireNonNull(automationId, "automationId");
        requireNonNull(changeType, "changeType");
        requireNonNull(changedAt, "changedAt");
    }
}
