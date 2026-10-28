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
 * Set the information of a SCIM provisioned user. Replaces the user attributes sent in the request.
 *
 * @param schemas SCIM schema used
 * @param displayName The name of the user, suitable for display to end-users
 * @param externalId The ID of the User
 * @param groups Associated groups
 * @param active The active status of the User
 * @param userName Configured by the admin. Could be an email, login, or username
 * @param name The components of the user's name
 * @param emails User emails
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/scim/set-information-for-provisioned-user/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetInformationForProvisionedUserPayload(
        @Nullable List<String> schemas,
        @JsonProperty("displayName") @Nullable String displayName,
        @JsonProperty("externalId") @Nullable String externalId,
        @Nullable List<String> groups,
        @Nullable Boolean active,
        @JsonProperty("userName") String userName,
        GitHubSetInformationForProvisionedUserName name,
        List<GitHubSetInformationForProvisionedUserEmail> emails) {

    public GitHubSetInformationForProvisionedUserPayload {
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
