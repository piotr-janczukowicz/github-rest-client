package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationActionsSecretTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubOrganizationActionsSecretBuilder builder = GitHubOrganizationActionsSecretBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsSecret())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubOrganizationActionsSecretBuilder builder = GitHubOrganizationActionsSecretBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsSecret())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubOrganizationActionsSecretBuilder builder = GitHubOrganizationActionsSecretBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsSecret())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenVisibilityNull() {
        final GitHubOrganizationActionsSecretBuilder builder = GitHubOrganizationActionsSecretBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsSecret())
                .visibility(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }
}
