package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Installation account ({@code simple-user} or {@code enterprise}). */
@CatalogSchema("components/schemas/installation/properties/account")
public sealed interface GitHubInstallationAccount
        permits GitHubInstallationAccount.SimpleUser, GitHubInstallationAccount.Enterprise {

    /**
     * Simple user account.
     *
     * @param user User
     */
    record SimpleUser(GitHubSimpleUser user) implements GitHubInstallationAccount {
        public SimpleUser {
            requireNonNull(user, "user");
        }
    }

    /**
     * Enterprise account.
     *
     * @param enterprise Enterprise
     */
    record Enterprise(GitHubEnterprise enterprise) implements GitHubInstallationAccount {
        public Enterprise {
            requireNonNull(enterprise, "enterprise");
        }
    }
}
