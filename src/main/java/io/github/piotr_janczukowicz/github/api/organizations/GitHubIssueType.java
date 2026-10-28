package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Issue Type. The type assigned to the issue. This is only present for issues in repositories where issue types are
 * supported.
 *
 * @param id The unique identifier of the issue type.
 * @param nodeId The node identifier of the issue type.
 * @param name The name of the issue type.
 * @param description The description of the issue type.
 * @param color The color of the issue type.
 * @param createdAt The time the issue type created.
 * @param updatedAt The time the issue type last updated.
 * @param isEnabled The enabled state of the issue type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue-type")
public record GitHubIssueType(
        long id,
        String nodeId,
        String name,
        @Nullable String description,
        @Nullable GitHubIssueTypeColor color,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable Boolean isEnabled) {

    public GitHubIssueType {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
    }
}
