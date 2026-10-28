package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Issue Event Rename.
 *
 * @param from From
 * @param to To
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue-event-rename")
@RecordBuilder
public record GitHubIssueEventRename(String from, String to) {

    public GitHubIssueEventRename {
        requireNonNull(from, "from");
        requireNonNull(to, "to");
    }
}
