package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Status update belonging to a project.
 *
 * @param id Id
 * @param nodeId Node id
 * @param projectNodeId Project node id
 * @param creator Creator
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param status Status
 * @param startDate Start date
 * @param targetDate Target date
 * @param body Body
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-status-update")
public record GitHubProjectStatusUpdate(
        @SuppressSchemaTypeMismatch("dump type is number without int64")
        long id,

        String nodeId,
        @Nullable String projectNodeId,
        @Nullable GitHubSimpleUser creator,
        Instant createdAt,
        Instant updatedAt,
        @Nullable GitHubProjectStatus status,
        @Nullable String startDate,
        @Nullable String targetDate,
        @Nullable String body) {

    public GitHubProjectStatusUpdate {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
