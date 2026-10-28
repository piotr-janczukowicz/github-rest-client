package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A member of a SCIM group to provision.
 *
 * @param value The local unique identifier for the member.
 * @param displayName The display name associated with the member.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/group/properties/members/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubScimEnterpriseGroupMember(
        String value, @JsonProperty("displayName") String displayName) {

    public GitHubScimEnterpriseGroupMember {
        requireNonNull(value, "value");
        requireNonNull(displayName, "displayName");
    }
}
