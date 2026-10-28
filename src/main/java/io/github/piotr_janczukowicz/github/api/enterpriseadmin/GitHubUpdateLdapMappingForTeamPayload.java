package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Request to update the LDAP mapping of a team.
 *
 * @param ldapDn The [distinguished name](https://www.ldap.com/ldap-dns-and-rdns) (DN) of the LDAP entry to map to a
 *     team.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-ldap-mapping-for-team/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdateLdapMappingForTeamPayload(String ldapDn) {
    public GitHubUpdateLdapMappingForTeamPayload {
        requireNonNull(ldapDn, "ldapDn");
    }
}
