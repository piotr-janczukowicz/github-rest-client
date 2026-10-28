package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotAlertTest {

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenDependencyNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .dependency(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("dependency");
    }

    @Test
    void constructor_shouldThrow_whenSecurityAdvisoryNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .securityAdvisory(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("securityAdvisory");
    }

    @Test
    void constructor_shouldThrow_whenSecurityVulnerabilityNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .securityVulnerability(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("securityVulnerability");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .assignees(null);

        final GitHubDependabotAlert sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenDismissedCommentTooLong() {
        final GitHubDependabotAlertBuilder builder = GitHubDependabotAlertBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlert())
                .dismissedComment("x".repeat(281));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dismissedComment");
    }
}
