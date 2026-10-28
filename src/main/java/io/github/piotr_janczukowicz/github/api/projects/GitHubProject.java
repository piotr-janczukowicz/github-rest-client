package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Projects v2 project.
 *
 * @param id Id
 * @param nodeId Node id
 * @param owner Owner
 * @param creator Creator
 * @param title Title
 * @param description Description
 * @param isPublic Whether the project is public
 * @param closedAt Closed at
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param number Project number
 * @param shortDescription Short description
 * @param deletedAt Deleted at
 * @param deletedBy Deleted by
 * @param state State
 * @param latestStatusUpdate Latest status update
 * @param isTemplate Whether this project is a template
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2")
public record GitHubProject(
        @SuppressSchemaTypeMismatch("dump type is number without int64")
        long id,

        String nodeId,
        GitHubSimpleUser owner,
        GitHubSimpleUser creator,
        String title,
        @Nullable String description,
        @JsonProperty("public") boolean isPublic,
        OptionalNull<Instant> closedAt,
        Instant createdAt,
        Instant updatedAt,
        int number,
        @Nullable String shortDescription,
        OptionalNull<Instant> deletedAt,
        OptionalNull<GitHubSimpleUser> deletedBy,
        @Nullable GitHubProjectState state,
        @Nullable GitHubProjectStatusUpdate latestStatusUpdate,
        @Nullable Boolean isTemplate) {

    public GitHubProject {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(owner, "owner");
        requireNonNull(creator, "creator");
        requireNonNull(title, "title");
        requireNonNull(closedAt, "closedAt");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(deletedAt, "deletedAt");
        requireNonNull(deletedBy, "deletedBy");
    }
}
