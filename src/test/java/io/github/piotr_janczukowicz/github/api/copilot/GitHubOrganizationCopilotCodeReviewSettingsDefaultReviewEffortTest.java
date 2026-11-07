package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortTest {

    @Test
    void constructor_shouldThrow_whenEffectiveNull() {
        final GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortBuilder builder =
                GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewSettingsDefaultReviewEffort())
                        .effective(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("effective");
    }

    @Test
    void constructor_shouldThrow_whenGithubDefaultNull() {
        final GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortBuilder builder =
                GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewSettingsDefaultReviewEffort())
                        .githubDefault(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("githubDefault");
    }

    @Test
    void constructor_shouldThrow_whenManagedByNull() {
        final GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortBuilder builder =
                GitHubOrganizationCopilotCodeReviewSettingsDefaultReviewEffortBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewSettingsDefaultReviewEffort())
                        .managedBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("managedBy");
    }
}
