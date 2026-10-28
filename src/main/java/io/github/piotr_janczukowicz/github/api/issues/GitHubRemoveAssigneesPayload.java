package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Remove assignees from an issue request body.
 *
 * @param assignees Usernames of assignees to remove from an issue. _NOTE: Only users with push access can remove
 *     assignees from an issue. Assignees are silently ignored otherwise._
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/remove-assignees/requestBody")
@RecordBuilder
public record GitHubRemoveAssigneesPayload(@Nullable List<String> assignees) {

    public GitHubRemoveAssigneesPayload {
        if (assignees != null) {
            assignees = List.copyOf(assignees);
        }
    }
}
