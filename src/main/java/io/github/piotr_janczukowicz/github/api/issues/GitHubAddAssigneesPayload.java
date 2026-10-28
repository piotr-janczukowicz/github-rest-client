package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Add assignees to an issue request body.
 *
 * @param assignees Usernames of people to assign this issue to. _NOTE: Only users with push access can add assignees to
 *     an issue. Assignees are silently ignored otherwise._
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/add-assignees/requestBody")
@RecordBuilder
public record GitHubAddAssigneesPayload(@Nullable List<GitHubAddAssigneesAssignee> assignees) {

    public GitHubAddAssigneesPayload {
        if (assignees != null) {
            assignees = List.copyOf(assignees);
        }
    }
}
