package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An external member linked to an external group.
 *
 * @param memberId The internal user ID of the identity
 * @param memberLogin The handle/login for the user
 * @param memberName The user display name/profile name
 * @param memberEmail An email attached to a user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/external-group/properties/members/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubExternalGroupMember(long memberId, String memberLogin, String memberName, String memberEmail) {

    public GitHubExternalGroupMember {
        requireNonNull(memberLogin, "memberLogin");
        requireNonNull(memberName, "memberName");
        requireNonNull(memberEmail, "memberEmail");
    }
}
