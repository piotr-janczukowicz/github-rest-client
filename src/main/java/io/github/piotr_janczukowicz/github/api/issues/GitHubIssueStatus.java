package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The status of the issue. Issue statuses are in preview and only available to accounts enrolled in the preview.
 *
 * @param id The stable identifier of the planning status.
 * @param name The display name of the planning status, which may change in the future.
 * @param category A stable identifier for the category, such as {@code backlog}, {@code planned}, {@code started},
 *     {@code completed}, {@code not_planned} or {@code duplicate}. Clients should expect new keys to be added.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"components/schemas/issue/properties/status", "operations/issues/update/200/properties/status"})
@RecordBuilder
public record GitHubIssueStatus(long id, String name, String category) {

    public GitHubIssueStatus {
        requireNonNull(name, "name");
        requireNonNull(category, "category");
    }
}
