package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLdapMappingUserTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .login(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenAvatarUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .avatarUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("avatarUrl");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenFollowersUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .followersUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("followersUrl");
    }

    @Test
    void constructor_shouldThrow_whenFollowingUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .followingUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("followingUrl");
    }

    @Test
    void constructor_shouldThrow_whenGistsUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .gistsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("gistsUrl");
    }

    @Test
    void constructor_shouldThrow_whenStarredUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .starredUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("starredUrl");
    }

    @Test
    void constructor_shouldThrow_whenSubscriptionsUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .subscriptionsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("subscriptionsUrl");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationsUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .organizationsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizationsUrl");
    }

    @Test
    void constructor_shouldThrow_whenReposUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .reposUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reposUrl");
    }

    @Test
    void constructor_shouldThrow_whenEventsUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .eventsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("eventsUrl");
    }

    @Test
    void constructor_shouldThrow_whenReceivedEventsUrlNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .receivedEventsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("receivedEventsUrl");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubLdapMappingUserBuilder builder = GitHubLdapMappingUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.ldapMappingUser())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }
}
