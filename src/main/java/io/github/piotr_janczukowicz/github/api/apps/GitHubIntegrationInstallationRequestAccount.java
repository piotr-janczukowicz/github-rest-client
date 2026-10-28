package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubEnterprise;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Account of an integration installation request ({@code simple-user} or {@code enterprise}). */
@CatalogSchema("components/schemas/integration-installation-request/properties/account")
public sealed interface GitHubIntegrationInstallationRequestAccount
        permits GitHubIntegrationInstallationRequestAccount.SimpleUser,
                GitHubIntegrationInstallationRequestAccount.Enterprise {

    /**
     * Simple user account.
     *
     * @param user User
     */
    record SimpleUser(GitHubSimpleUser user) implements GitHubIntegrationInstallationRequestAccount {
        public SimpleUser {
            requireNonNull(user, "user");
        }
    }

    /**
     * Enterprise account.
     *
     * @param enterprise Enterprise
     */
    record Enterprise(GitHubEnterprise enterprise) implements GitHubIntegrationInstallationRequestAccount {
        public Enterprise {
            requireNonNull(enterprise, "enterprise");
        }
    }
}
