package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * User access restrictions request body.
 *
 * @param users Usernames with push access
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/set-user-access-restrictions/requestBody",
    "operations/repos/add-user-access-restrictions/requestBody",
    "operations/repos/remove-user-access-restrictions/requestBody"
})
public record GitHubUserAccessRestrictionsPayload(List<String> users) {

    private static final String PARAM_USERS = "users";

    public GitHubUserAccessRestrictionsPayload {
        users = List.copyOf(requireNonNull(users, PARAM_USERS));
    }
}
