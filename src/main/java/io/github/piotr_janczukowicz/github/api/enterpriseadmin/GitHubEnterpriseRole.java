package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubEnterprise;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Enterprise custom role.
 *
 * @param id The unique identifier of the role.
 * @param name The name of the role.
 * @param description A short description about who this role is for or what permissions it grants.
 * @param source Source answers the question, "where did this role come from?"
 * @param permissions A list of permissions included in this role.
 * @param enterprise The enterprise the role belongs to.
 * @param createdAt The date and time the role was created.
 * @param updatedAt The date and time the role was last updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-role")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseRole(
        long id,
        String name,
        @Nullable String description,
        @Nullable GitHubEnterpriseRoleSource source,
        List<String> permissions,
        @Nullable GitHubEnterprise enterprise,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubEnterpriseRole {
        requireNonNull(name, "name");
        requireNonNull(permissions, "permissions");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
