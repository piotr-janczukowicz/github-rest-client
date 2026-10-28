package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Item belonging to a project with untyped content and field values.
 *
 * @param id Id
 * @param nodeId Node id
 * @param projectUrl Project url
 * @param contentType Content type
 * @param content Content
 * @param creator Creator
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param archivedAt Archived at
 * @param itemUrl Item url
 * @param fields Field values
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-item-with-content")
public record GitHubProjectItem(
        @SuppressSchemaTypeMismatch("dump type is number without int64")
        long id,

        @Nullable String nodeId,
        @Nullable String projectUrl,

        GitHubProjectItemContentType contentType,
        @Nullable Map<String, Object> content,
        @Nullable GitHubSimpleUser creator,
        Instant createdAt,
        Instant updatedAt,
        OptionalNull<Instant> archivedAt,
        @Nullable String itemUrl,

        @Nullable List<Map<String, Object>> fields) {

    public GitHubProjectItem {
        requireNonNull(contentType, "contentType");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(archivedAt, "archivedAt");
    }
}
