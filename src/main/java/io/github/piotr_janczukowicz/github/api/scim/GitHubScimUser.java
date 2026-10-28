package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
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
 * A SCIM provisioned user of an organization.
 *
 * @param schemas SCIM schema used
 * @param id Unique identifier of an external identity
 * @param externalId The ID of the User
 * @param userName Configured by the admin. Could be an email, login, or username
 * @param profileUrl The URL of the linked GitHub user's profile
 * @param displayName The name of the user, suitable for display to end-users
 * @param name The components of the user's name
 * @param emails User emails
 * @param active The active status of the User
 * @param meta Metadata of the SCIM resource
 * @param organizationId The ID of the organization
 * @param operations Set of operations performed on the user
 * @param groups Associated groups
 * @param roles Associated roles
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scim-user")
@Flavors(GitHubFlavor.GHEC)
public record GitHubScimUser(
        List<String> schemas,
        String id,
        @JsonProperty("externalId") @Nullable String externalId,
        @JsonProperty("userName") @Nullable String userName,
        @JsonProperty("profileUrl") @Nullable String profileUrl,
        @JsonProperty("displayName") @Nullable String displayName,
        @Nullable GitHubScimUserName name,
        List<GitHubScimUserEmail> emails,
        boolean active,
        GitHubScimUserMeta meta,
        @Nullable Long organizationId,
        @Nullable List<GitHubScimUserOperation> operations,
        @Nullable List<GitHubScimUserGroup> groups,
        @Nullable List<GitHubScimUserRole> roles) {

    public GitHubScimUser {
        requireNonEmpty(schemas, "schemas");
        requireNonNull(id, "id");
        requireNonNull(emails, "emails");
        requireNonNull(meta, "meta");
    }
}
