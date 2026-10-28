package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A role assigned to a SCIM user.
 *
 * @param display The display name of the role.
 * @param type The type of role.
 * @param value The role value representing a user role in GitHub.
 * @param primary Whether the role is a primary role for the user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user-role/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUserRole(
        @Nullable String display,
        @Nullable String type,
        GitHubUserRoleValue value,
        @Nullable Boolean primary) {

    public GitHubUserRole {
        requireNonNull(value, "value");
    }
}
