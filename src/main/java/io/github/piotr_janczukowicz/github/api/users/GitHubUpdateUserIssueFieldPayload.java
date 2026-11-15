package io.github.piotr_janczukowicz.github.api.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update user issue field payload.
 *
 * @param name Name of the issue field.
 * @param description Description of the issue field.
 * @param visibility The visibility of the issue field. Can be {@code restricted} (visible only to the owner and to
 *     collaborators on the repository the field is read through) or {@code all} (visible to all users who can see
 *     issues).
 * @param options Options for select fields. Only applicable when updating single_select or multi_select fields. When
 *     provided, this array **replaces** the entire existing set of options rather than adding to or updating individual
 *     options. To retain or update an existing option, include it in the array with its {@code id}. Options sent
 *     without an {@code id} are treated as new options and may cause existing options to be deleted and recreated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/user-update-issue-field",
    "operations/users/update-issue-field-for-authenticated-user/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateUserIssueFieldPayload(
        @Nullable String name,
        @Nullable OptionalNull<String> description,
        @Nullable GitHubUpdateUserIssueFieldVisibility visibility,
        @Nullable List<GitHubUpdateUserIssueFieldOption> options) {
    public GitHubUpdateUserIssueFieldPayload {
        if (options != null) {
            options = List.copyOf(options);
        }
    }
}
