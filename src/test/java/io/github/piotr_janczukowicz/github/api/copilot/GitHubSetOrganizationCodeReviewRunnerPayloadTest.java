package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetOrganizationCodeReviewRunnerPayloadTest {

    @Test
    void constructor_shouldThrow_whenRunnerTypeNull() {
        final GitHubSetOrganizationCodeReviewRunnerPayloadBuilder builder =
                GitHubSetOrganizationCodeReviewRunnerPayloadBuilder.builder(
                                CopilotCatalogFixtures.setOrganizationCodeReviewRunnerPayload())
                        .runnerType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnerType");
    }

    @Test
    void constructor_shouldAcceptNull_whenRunnerLabelsNull() {
        final GitHubSetOrganizationCodeReviewRunnerPayload value =
                GitHubSetOrganizationCodeReviewRunnerPayloadBuilder.builder(
                                CopilotCatalogFixtures.setOrganizationCodeReviewRunnerPayload())
                        .runnerLabels(null)
                        .build();

        assertThat(value.runnerLabels()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenRunnerLabelsGiven() {
        final List<String> source = new ArrayList<>(List.of("runner_labels"));
        final GitHubSetOrganizationCodeReviewRunnerPayload value =
                GitHubSetOrganizationCodeReviewRunnerPayloadBuilder.builder(
                                CopilotCatalogFixtures.setOrganizationCodeReviewRunnerPayload())
                        .runnerLabels(source)
                        .build();

        source.add("runner_labels");

        assertThat(value.runnerLabels()).hasSize(source.size() - 1);
    }
}
