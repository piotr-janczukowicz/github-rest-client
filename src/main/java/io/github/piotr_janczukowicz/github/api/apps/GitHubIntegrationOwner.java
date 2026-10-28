package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubEnterprise;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Integration owner ({@code oneOf} simple-user vs enterprise). */
@CatalogSchema("components/schemas/integration/properties/owner")
public sealed interface GitHubIntegrationOwner
        permits GitHubIntegrationOwner.SimpleUser, GitHubIntegrationOwner.Enterprise {

    /**
     * Owner as a simple user.
     *
     * @param user simple user
     * @return integration owner
     */
    static GitHubIntegrationOwner ofUser(final GitHubSimpleUser user) {
        return new SimpleUser(user);
    }

    /**
     * Owner as an enterprise.
     *
     * @param enterprise enterprise
     * @return integration owner
     */
    static GitHubIntegrationOwner ofEnterprise(final GitHubEnterprise enterprise) {
        return new Enterprise(enterprise);
    }

    /**
     * Simple user owner.
     *
     * @param user user
     */
    record SimpleUser(GitHubSimpleUser user) implements GitHubIntegrationOwner {
        public SimpleUser {
            requireNonNull(user, "user");
        }
    }

    /**
     * Enterprise owner.
     *
     * @param enterprise enterprise
     */
    record Enterprise(GitHubEnterprise enterprise) implements GitHubIntegrationOwner {
        public Enterprise {
            requireNonNull(enterprise, "enterprise");
        }
    }
}
