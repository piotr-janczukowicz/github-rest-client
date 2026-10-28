package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The billing plan of an LDAP-mapped user.
 *
 * @param collaborators collaborators
 * @param name name
 * @param space space
 * @param privateRepos private repos
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ldap-mapping-user/properties/plan")
@Flavors(GitHubFlavor.GHES)
public record GitHubLdapMappingUserPlan(int collaborators, String name, int space, int privateRepos) {
    public GitHubLdapMappingUserPlan {
        requireNonNull(name, "name");
    }
}
