package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCopilotCodeReviewRunnerTest {

    @Test
    void constructor_shouldThrow_whenRunnerTypeNull() {
        final GitHubOrganizationCopilotCodeReviewRunnerBuilder builder =
                GitHubOrganizationCopilotCodeReviewRunnerBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewRunner())
                        .runnerType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnerType");
    }

    @Test
    void constructor_shouldThrow_whenRunnerLabelsNull() {
        final GitHubOrganizationCopilotCodeReviewRunnerBuilder builder =
                GitHubOrganizationCopilotCodeReviewRunnerBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewRunner())
                        .runnerLabels(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnerLabels");
    }

    @Test
    void constructor_shouldThrow_whenConfiguredByNull() {
        final GitHubOrganizationCopilotCodeReviewRunnerBuilder builder =
                GitHubOrganizationCopilotCodeReviewRunnerBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewRunner())
                        .configuredBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("configuredBy");
    }
}
