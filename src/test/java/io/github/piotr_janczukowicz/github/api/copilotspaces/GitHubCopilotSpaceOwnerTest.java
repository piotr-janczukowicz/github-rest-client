package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSpaceOwnerTest {

    @Test
    void ofUser_shouldStoreValue_whenCalled() {
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();

        final GitHubCopilotSpaceOwner owner = GitHubCopilotSpaceOwner.ofUser(user);

        assertThat(owner).isEqualTo(new GitHubCopilotSpaceOwner.SimpleUser(user));
    }

    @Test
    void ofUser_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotSpaceOwner.ofUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void ofOrganization_shouldStoreValue_whenCalled() {
        final GitHubOrganizationSimple organization = OrganizationsCatalogFixtures.organizationSimple();

        final GitHubCopilotSpaceOwner owner = GitHubCopilotSpaceOwner.ofOrganization(organization);

        assertThat(owner).isEqualTo(new GitHubCopilotSpaceOwner.Organization(organization));
    }

    @Test
    void ofOrganization_shouldThrow_whenOrganizationNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotSpaceOwner.ofOrganization(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organization");
    }

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotSpaceOwner.SimpleUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotSpaceOwner.Organization(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organization");
    }
}
