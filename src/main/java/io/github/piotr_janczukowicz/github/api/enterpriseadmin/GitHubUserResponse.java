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
 * A provisioned SCIM user of an enterprise.
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
@CatalogSchema("components/schemas/user-response")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUserResponse(
        List<GitHubUserResponseSchemaUrn> schemas,
        @JsonProperty("externalId") @Nullable String externalId,
        boolean active,
        @JsonProperty("userName") @Nullable String userName,
        @Nullable GitHubUserNameResponse name,
        @JsonProperty("displayName") @Nullable String displayName,
        List<GitHubUserEmailResponse> emails,
        @Nullable List<GitHubUserRole> roles) {

    public GitHubUserResponse {
        requireNonNull(schemas, "schemas");
        requireNonNull(emails, "emails");
    }
}
