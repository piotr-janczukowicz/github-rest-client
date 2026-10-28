package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * SCIM user to provision in an enterprise.
 *
 * @param schemas The URIs that are used to indicate the namespaces of the SCIM schemas.
 * @param externalId A unique identifier for the resource as defined by the provisioning client.
 * @param active Whether the user active in the IdP.
 * @param userName The username for the user.
 * @param name The components of the user name.
 * @param displayName A human-readable name for the user.
 * @param emails The emails for the user.
 * @param roles The roles assigned to the user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubScimEnterpriseUser(
        List<GitHubScimEnterpriseUserSchemaUrn> schemas,
        @JsonProperty("externalId") String externalId,
        boolean active,
        @JsonProperty("userName") String userName,
        @Nullable GitHubUserName name,
        @JsonProperty("displayName") String displayName,
        List<GitHubUserEmail> emails,
        @Nullable List<GitHubUserRole> roles) {

    public GitHubScimEnterpriseUser {
        requireNonNull(schemas, "schemas");
        schemas = List.copyOf(schemas);
        requireNonNull(externalId, "externalId");
        requireNonNull(userName, "userName");
        requireNonNull(displayName, "displayName");
        requireNonNull(emails, "emails");
        emails = List.copyOf(emails);
        if (roles != null) {
            roles = List.copyOf(roles);
        }
    }
}
