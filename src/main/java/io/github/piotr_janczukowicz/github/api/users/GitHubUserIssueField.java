package io.github.piotr_janczukowicz.github.api.users;

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
 * A custom attribute defined by a personal account for attaching structured data to issues.
 *
 * @param id The unique identifier of the issue field.
 * @param nodeId The node identifier of the issue field.
 * @param name The name of the issue field.
 * @param description The description of the issue field.
 * @param dataType The data type of the issue field.
 * @param visibility The visibility of the issue field. Can be {@code restricted} (visible only to the owner and to
 *     collaborators on the repository the field is read through) or {@code all} (visible to all users who can see
 *     issues).
 * @param options Available options for single select and multi select fields.
 * @param createdAt The time the issue field was created.
 * @param updatedAt The time the issue field was updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user-issue-field")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUserIssueField(
        long id,
        String nodeId,
        String name,
        @Nullable String description,
        GitHubUserIssueFieldDataType dataType,
        @Nullable GitHubUserIssueFieldVisibility visibility,
        @Nullable List<GitHubUserIssueFieldOption> options,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {
    public GitHubUserIssueField {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(dataType, "dataType");
    }
}
