package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotAlertWithRepositoryTest {

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenDependencyNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .dependency(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("dependency");
    }

    @Test
    void constructor_shouldThrow_whenSecurityAdvisoryNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .securityAdvisory(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("securityAdvisory");
    }

    @Test
    void constructor_shouldThrow_whenSecurityVulnerabilityNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .securityVulnerability(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("securityVulnerability");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .repository(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .assignees(null);

        final GitHubDependabotAlertWithRepository sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenDismissedCommentTooLong() {
        final GitHubDependabotAlertWithRepositoryBuilder builder = GitHubDependabotAlertWithRepositoryBuilder.builder(
                        DependabotCatalogFixtures.dependabotAlertWithRepository())
                .dismissedComment("x".repeat(281));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dismissedComment");
    }
}
