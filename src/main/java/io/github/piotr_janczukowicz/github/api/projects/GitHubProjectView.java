package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * View inside a projects v2 project.
 *
 * @param id Id
 * @param number View number
 * @param name Name
 * @param layout Layout
 * @param nodeId Node id
 * @param projectUrl Project url
 * @param htmlUrl Html url
 * @param creator Creator
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param filter Filter query
 * @param visibleFields Visible field ids
 * @param sortBy Sort configuration
 * @param groupBy Horizontal grouping field ids
 * @param verticalGroupBy Vertical grouping field ids
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-view")
public record GitHubProjectView(
        long id,
        int number,
        String name,
        GitHubProjectViewLayout layout,
        String nodeId,
        String projectUrl,
        String htmlUrl,

        GitHubSimpleUser creator,
        Instant createdAt,
        Instant updatedAt,
        @Nullable String filter,
        List<Long> visibleFields,
        List<GitHubProjectViewSort> sortBy,
        List<Long> groupBy,
        List<Long> verticalGroupBy) {

    public GitHubProjectView {
        requireNonNull(name, "name");
        requireNonNull(layout, "layout");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(projectUrl, "projectUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(creator, "creator");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(visibleFields, "visibleFields");
        requireNonNull(sortBy, "sortBy");
        requireNonNull(groupBy, "groupBy");
        requireNonNull(verticalGroupBy, "verticalGroupBy");
    }
}
