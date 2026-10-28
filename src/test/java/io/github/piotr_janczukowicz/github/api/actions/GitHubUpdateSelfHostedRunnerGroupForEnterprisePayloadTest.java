package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateSelfHostedRunnerGroupForEnterprisePayloadTest {

    @Test
    void constructor_shouldCopySelectedWorkflows_whenSelectedWorkflowsGiven() {
        final List<String> source = new ArrayList<>(List.of("selectedWorkflows-sample"));
        final GitHubUpdateSelfHostedRunnerGroupForEnterprisePayload payload =
                GitHubUpdateSelfHostedRunnerGroupForEnterprisePayloadBuilder.builder(
                                ActionsCatalogFixtures.updateSelfHostedRunnerGroupForEnterprisePayload())
                        .selectedWorkflows(source)
                        .build();

        source.add("other-sample");
        final List<String> selectedWorkflows = payload.selectedWorkflows();

        final Throwable thrown = catchThrowable(() -> selectedWorkflows.add("other-sample"));

        assertSoftly(softly -> {
            softly.assertThat(payload.selectedWorkflows()).containsExactly("selectedWorkflows-sample");
            softly.assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldAcceptNullSelectedWorkflows_whenSelectedWorkflowsNull() {
        final GitHubUpdateSelfHostedRunnerGroupForEnterprisePayload payload =
                new GitHubUpdateSelfHostedRunnerGroupForEnterprisePayload(null, null, null, null, null, null);

        assertThat(payload.selectedWorkflows()).isNull();
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubUpdateSelfHostedRunnerGroupForEnterprisePayload value =
                ActionsCatalogFixtures.updateSelfHostedRunnerGroupForEnterprisePayload();

        assertSoftly(softly -> {
            softly.assertThat(value.name()).isEqualTo("name-sample");
            softly.assertThat(value.visibility()).isEqualTo(GitHubRunnerGroupVisibilityForEnterprise.SELECTED);
            softly.assertThat(value.allowsPublicRepositories()).isTrue();
            softly.assertThat(value.restrictedToWorkflows()).isTrue();
            softly.assertThat(value.selectedWorkflows()).containsExactly("selectedWorkflows-sample");
            softly.assertThat(value.networkConfigurationId()).isEqualTo("networkConfigurationId-sample");
        });
    }
}
