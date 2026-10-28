package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Issue Event Milestone.
 *
 * @param title Title
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue-event-milestone")
@RecordBuilder
public record GitHubIssueEventMilestone(String title) {

    public GitHubIssueEventMilestone {
        requireNonNull(title, "title");
    }
}
