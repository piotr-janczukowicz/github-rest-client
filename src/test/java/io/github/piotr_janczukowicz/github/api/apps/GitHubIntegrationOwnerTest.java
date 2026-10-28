package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubEnterprise;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIntegrationOwnerTest {

    @Test
    void ofUser_shouldStoreValue_whenCalled() {
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();

        final GitHubIntegrationOwner owner = GitHubIntegrationOwner.ofUser(user);

        assertThat(owner).isEqualTo(new GitHubIntegrationOwner.SimpleUser(user));
    }

    @Test
    void ofUser_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIntegrationOwner.ofUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void ofEnterprise_shouldStoreValue_whenCalled() {
        final GitHubEnterprise enterprise = OrganizationsCatalogFixtures.enterprise();

        final GitHubIntegrationOwner owner = GitHubIntegrationOwner.ofEnterprise(enterprise);

        assertThat(owner).isEqualTo(new GitHubIntegrationOwner.Enterprise(enterprise));
    }

    @Test
    void ofEnterprise_shouldThrow_whenEnterpriseNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIntegrationOwner.ofEnterprise(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enterprise");
    }

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIntegrationOwner.SimpleUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenEnterpriseNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIntegrationOwner.Enterprise(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enterprise");
    }
}
