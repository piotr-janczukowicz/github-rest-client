package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request to update a custom repository role.
 *
 * @param name The name of the custom role
 * @param description A short description of the custom role
 * @param baseRole The system role from which this role inherits permissions
 * @param permissions The permissions included in this role
 * @deprecated The operation is closing down; use the update custom repository role operation instead.
 */
@Deprecated(since = "2023-03-06")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/update-custom-role/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateCustomRolePayload(
        @Nullable String name,
        @Nullable String description,
        @Nullable GitHubCustomRepositoryRoleUpdateBaseRole baseRole,
        @Nullable List<String> permissions) {

    public GitHubUpdateCustomRolePayload {
        if (permissions != null) {
            permissions = List.copyOf(permissions);
        }
    }
}
