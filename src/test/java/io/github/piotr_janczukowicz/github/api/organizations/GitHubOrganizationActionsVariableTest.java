package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationActionsVariableTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubOrganizationActionsVariableBuilder builder = GitHubOrganizationActionsVariableBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsVariable())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenValueNull() {
        final GitHubOrganizationActionsVariableBuilder builder = GitHubOrganizationActionsVariableBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsVariable())
                .value(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubOrganizationActionsVariableBuilder builder = GitHubOrganizationActionsVariableBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsVariable())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubOrganizationActionsVariableBuilder builder = GitHubOrganizationActionsVariableBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsVariable())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenVisibilityNull() {
        final GitHubOrganizationActionsVariableBuilder builder = GitHubOrganizationActionsVariableBuilder.builder(
                        OrganizationsCatalogFixtures.organizationActionsVariable())
                .visibility(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }
}
