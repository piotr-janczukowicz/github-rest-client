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
 * Draft issue in a project.
 *
 * @param id Id
 * @param nodeId Node id
 * @param title Title
 * @param body Body
 * @param user User
 * @param createdAt Created at
 * @param updatedAt Updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-draft-issue")
public record GitHubProjectDraftIssue(
        @SuppressSchemaTypeMismatch("dump type is number without int64")
        long id,

        String nodeId,
        String title,
        @Nullable String body,
        @Nullable GitHubSimpleUser user,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubProjectDraftIssue {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(title, "title");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
