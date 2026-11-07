package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCopilotCodeReviewPolicyTest {

    @Test
    void constructor_shouldThrow_whenEffectiveStateNull() {
        final GitHubOrganizationCopilotCodeReviewPolicyBuilder builder =
                GitHubOrganizationCopilotCodeReviewPolicyBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewPolicy())
                        .effectiveState(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("effectiveState");
    }

    @Test
    void constructor_shouldThrow_whenManagedByNull() {
        final GitHubOrganizationCopilotCodeReviewPolicyBuilder builder =
                GitHubOrganizationCopilotCodeReviewPolicyBuilder.builder(
                                CopilotCatalogFixtures.organizationCopilotCodeReviewPolicy())
                        .managedBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("managedBy");
    }
}
