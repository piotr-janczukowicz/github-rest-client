package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** The user or organization that owns this space ({@code anyOf} simple-user vs organization-simple). */
@CatalogSchema("components/schemas/copilot-space/properties/owner")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public sealed interface GitHubCopilotSpaceOwner
        permits GitHubCopilotSpaceOwner.SimpleUser, GitHubCopilotSpaceOwner.Organization {

    /**
     * Owner as a simple user.
     *
     * @param user simple user
     * @return space owner
     */
    static GitHubCopilotSpaceOwner ofUser(final GitHubSimpleUser user) {
        return new SimpleUser(user);
    }

    /**
     * Owner as an organization.
     *
     * @param organization organization
     * @return space owner
     */
    static GitHubCopilotSpaceOwner ofOrganization(final GitHubOrganizationSimple organization) {
        return new Organization(organization);
    }

    /**
     * Simple user owner.
     *
     * @param user user
     */
    record SimpleUser(GitHubSimpleUser user) implements GitHubCopilotSpaceOwner {
        public SimpleUser {
            requireNonNull(user, "user");
        }
    }

    /**
     * Organization owner.
     *
     * @param organization organization
     */
    record Organization(GitHubOrganizationSimple organization) implements GitHubCopilotSpaceOwner {
        public Organization {
            requireNonNull(organization, "organization");
        }
    }
}
