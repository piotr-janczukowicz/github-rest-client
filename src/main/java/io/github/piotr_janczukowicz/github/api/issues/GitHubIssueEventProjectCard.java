package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Issue Event Project Card.
 *
 * @param url Url
 * @param id Id
 * @param projectUrl Project url
 * @param projectId Project id
 * @param columnName Column name
 * @param previousColumnName Previous column name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue-event-project-card")
@RecordBuilder
public record GitHubIssueEventProjectCard(
        String url,
        long id,
        String projectUrl,
        long projectId,
        String columnName,
        @Nullable String previousColumnName) {

    public GitHubIssueEventProjectCard {
        requireNonNull(url, "url");
        requireNonNull(projectUrl, "projectUrl");
        requireNonNull(columnName, "columnName");
    }
}
