package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetOrganizationCodeReviewPolicyPayloadTest {

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubSetOrganizationCodeReviewPolicyPayloadBuilder builder =
                GitHubSetOrganizationCodeReviewPolicyPayloadBuilder.builder(
                                CopilotCatalogFixtures.setOrganizationCodeReviewPolicyPayload())
                        .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }
}
