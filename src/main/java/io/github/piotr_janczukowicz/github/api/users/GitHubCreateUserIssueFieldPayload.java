package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create user issue field payload.
 *
 * @param name Name of the issue field.
 * @param description Description of the issue field.
 * @param dataType The data type of the issue field.
 * @param visibility The visibility of the issue field. Can be {@code restricted} (visible only to the owner and to
 *     collaborators on the repository the field is read through) or {@code all} (visible to all users who can see
 *     issues). Defaults to {@code restricted}.
 * @param options Options for select fields. Required when data_type is 'single_select' or 'multi_select'.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/user-create-issue-field",
    "operations/users/create-issue-field-for-authenticated-user/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateUserIssueFieldPayload(
        String name,
        @Nullable OptionalNull<String> description,
        GitHubCreateUserIssueFieldDataType dataType,
        @Nullable GitHubCreateUserIssueFieldVisibility visibility,
        @Nullable List<GitHubCreateUserIssueFieldOption> options) {
    public GitHubCreateUserIssueFieldPayload {
        requireNonNull(name, "name");
        requireNonNull(dataType, "dataType");
        if (options != null) {
            options = List.copyOf(options);
        }
    }
}
