package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Item belonging to a project with typed content.
 *
 * @param id Id
 * @param nodeId Node id
 * @param content Content
 * @param contentType Content type
 * @param creator Creator
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param archivedAt Archived at; {@code null} when omitted, {@link OptionalNull#ofNull()} when JSON null
 * @param projectUrl Project url
 * @param itemUrl Item url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-item-simple")
public record GitHubProjectItemSimple(
        @SuppressSchemaTypeMismatch("dump type is number without int64")
        long id,

        @Nullable String nodeId,
        @Nullable GitHubProjectItemContent content,
        GitHubProjectItemContentType contentType,
        @Nullable GitHubSimpleUser creator,
        Instant createdAt,
        Instant updatedAt,
        @Nullable OptionalNull<Instant> archivedAt,
        @Nullable String projectUrl,
        @Nullable String itemUrl) {

    public GitHubProjectItemSimple {
        requireNonNull(contentType, "contentType");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
