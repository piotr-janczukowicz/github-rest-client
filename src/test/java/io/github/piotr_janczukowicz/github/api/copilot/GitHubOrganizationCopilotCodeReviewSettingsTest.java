package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCopilotCodeReviewSettingsTest {

    @Test
    void constructor_shouldThrow_whenDefaultReviewEffortNull() {
        final GitHubOrganizationCopilotCodeReviewSettingsBuilder builder =
                GitHubOrganizationCopilotCodeReviewSettingsBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewSettings())
                        .defaultReviewEffort(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("defaultReviewEffort");
    }

    @Test
    void constructor_shouldThrow_whenAvailableReviewEffortsNull() {
        final GitHubOrganizationCopilotCodeReviewSettingsBuilder builder =
                GitHubOrganizationCopilotCodeReviewSettingsBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewSettings())
                        .availableReviewEfforts(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("availableReviewEfforts");
    }
}
