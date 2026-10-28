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
 * Provision and invite a SCIM user into an organization.
 *
 * @param userName Configured by the admin. Could be an email, login, or username
 * @param displayName The name of the user, suitable for display to end-users
 * @param name The components of the user's name
 * @param emails User emails
 * @param schemas SCIM schema used
 * @param externalId The ID of the User
 * @param groups Associated groups
 * @param active The active status of the User
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/scim/provision-and-invite-user/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubProvisionAndInviteUserPayload(
        @JsonProperty("userName") String userName,
        @JsonProperty("displayName") @Nullable String displayName,
        GitHubProvisionAndInviteUserName name,
        List<GitHubProvisionAndInviteUserEmail> emails,
        @Nullable List<String> schemas,
        @JsonProperty("externalId") @Nullable String externalId,
        @Nullable List<String> groups,
        @Nullable Boolean active) {

    public GitHubProvisionAndInviteUserPayload {
        requireNonNull(userName, "userName");
        requireNonNull(name, "name");
        emails = List.copyOf(requireNonEmpty(emails, "emails"));
        if (schemas != null) {
            schemas = List.copyOf(schemas);
        }
        if (groups != null) {
            groups = List.copyOf(groups);
        }
    }
}
