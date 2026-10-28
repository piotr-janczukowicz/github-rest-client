package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Organization membership.
 *
 * @param url Url
 * @param state State
 * @param role Role
 * @param directMembership Whether the user has direct membership in the organization
 * @param enterpriseTeamsProvidingIndirectMembership Enterprise team slugs providing indirect membership
 * @param organizationUrl Organization url
 * @param organization Organization
 * @param user User
 * @param permissions Permissions
 */
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/org-membership")
public record GitHubOrgMembership(
        String url,
        GitHubOrgMembershipState state,
        GitHubOrgMembershipRole role,
        @Nullable Boolean directMembership,
        @Nullable List<String> enterpriseTeamsProvidingIndirectMembership,
        String organizationUrl,
        GitHubOrganizationSimple organization,
        @Nullable GitHubSimpleUser user,
        @Nullable GitHubOrgMembershipPermissions permissions) {

    public GitHubOrgMembership {
        requireNonNull(url, "url");
        requireNonNull(state, "state");
        requireNonNull(role, "role");
        requireNonNull(organizationUrl, "organizationUrl");
        requireNonNull(organization, "organization");
        if (enterpriseTeamsProvidingIndirectMembership != null) {
            requireSizeAtMost(
                    enterpriseTeamsProvidingIndirectMembership, 100, "enterpriseTeamsProvidingIndirectMembership");
        }
    }
}
