package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateSelfHostedRunnerGroupForOrgPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final GitHubRunnerGroupVisibility visibility = GitHubRunnerGroupVisibility.SELECTED;
        final Boolean allowsPublicRepositories = true;
        final Boolean restrictedToWorkflows = true;
        final List<String> selectedWorkflows = List.of("selectedWorkflows-sample");
        final String networkConfigurationId = "networkConfigurationId-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubUpdateSelfHostedRunnerGroupForOrgPayload(
                null,
                visibility,
                allowsPublicRepositories,
                restrictedToWorkflows,
                selectedWorkflows,
                networkConfigurationId));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldCopySelectedWorkflows_whenSelectedWorkflowsGiven() {
        final List<String> source = new ArrayList<>(ActionsCatalogFixtures.updateSelfHostedRunnerGroupForOrgPayload()
                .selectedWorkflows());
        final int size = source.size();
        final GitHubUpdateSelfHostedRunnerGroupForOrgPayload payload =
                GitHubUpdateSelfHostedRunnerGroupForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.updateSelfHostedRunnerGroupForOrgPayload())
                        .selectedWorkflows(source)
                        .build();

        source.add(source.getFirst());
        final List<String> selectedWorkflows = payload.selectedWorkflows();
        final String first = source.getFirst();

        final Throwable thrown = catchThrowable(() -> selectedWorkflows.add(first));

        assertSoftly(softly -> {
            softly.assertThat(payload.selectedWorkflows()).hasSize(size);
            softly.assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldAcceptNullSelectedWorkflows_whenSelectedWorkflowsNull() {
        final GitHubUpdateSelfHostedRunnerGroupForOrgPayload payload =
                GitHubUpdateSelfHostedRunnerGroupForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.updateSelfHostedRunnerGroupForOrgPayload())
                        .selectedWorkflows(null)
                        .build();

        assertThat(payload.selectedWorkflows()).isNull();
    }
}
