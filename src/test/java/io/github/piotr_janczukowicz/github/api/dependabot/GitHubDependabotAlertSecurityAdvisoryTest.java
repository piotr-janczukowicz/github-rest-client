package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotAlertSecurityAdvisoryTest {

    @Test
    void constructor_shouldThrow_whenGhsaIdNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .ghsaId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ghsaId");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .description(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenVulnerabilitiesNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .vulnerabilities(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("vulnerabilities");
    }

    @Test
    void constructor_shouldThrow_whenSeverityNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .severity(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("severity");
    }

    @Test
    void constructor_shouldThrow_whenCwesNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .cwes(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cwes");
    }

    @Test
    void constructor_shouldThrow_whenIdentifiersNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .identifiers(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("identifiers");
    }

    @Test
    void constructor_shouldThrow_whenReferencesNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .references(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("references");
    }

    @Test
    void constructor_shouldThrow_whenPublishedAtNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .publishedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("publishedAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenSummaryTooLong() {
        final GitHubDependabotAlertSecurityAdvisoryBuilder builder =
                GitHubDependabotAlertSecurityAdvisoryBuilder.builder(
                                DependabotCatalogFixtures.dependabotAlertSecurityAdvisory())
                        .summary("x".repeat(1025));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("summary");
    }
}
