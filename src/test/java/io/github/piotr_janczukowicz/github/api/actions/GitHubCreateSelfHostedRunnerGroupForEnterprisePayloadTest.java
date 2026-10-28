package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateSelfHostedRunnerGroupForEnterprisePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateSelfHostedRunnerGroupForEnterprisePayload(
                null, null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldCopyLists_whenListsGiven() {
        final List<Long> organizationIds = new ArrayList<>(List.of(1L));
        final List<Long> runners = new ArrayList<>(List.of(1L));
        final List<String> selectedWorkflows = new ArrayList<>(List.of("selectedWorkflows-sample"));
        final GitHubCreateSelfHostedRunnerGroupForEnterprisePayload payload =
                GitHubCreateSelfHostedRunnerGroupForEnterprisePayloadBuilder.builder(
                                ActionsCatalogFixtures.createSelfHostedRunnerGroupForEnterprisePayload())
                        .selectedOrganizationIds(organizationIds)
                        .runners(runners)
                        .selectedWorkflows(selectedWorkflows)
                        .build();

        organizationIds.add(2L);
        runners.add(2L);
        selectedWorkflows.add("other-sample");

        assertSoftly(softly -> {
            softly.assertThat(payload.selectedOrganizationIds()).containsExactly(1L);
            softly.assertThat(payload.runners()).containsExactly(1L);
            softly.assertThat(payload.selectedWorkflows()).containsExactly("selectedWorkflows-sample");
        });
    }

    @Test
    void constructor_shouldAcceptNullLists_whenListsNull() {
        final GitHubCreateSelfHostedRunnerGroupForEnterprisePayload payload =
                new GitHubCreateSelfHostedRunnerGroupForEnterprisePayload(
                        "name-sample", null, null, null, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(payload.selectedOrganizationIds()).isNull();
            softly.assertThat(payload.runners()).isNull();
            softly.assertThat(payload.selectedWorkflows()).isNull();
        });
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubCreateSelfHostedRunnerGroupForEnterprisePayload value =
                ActionsCatalogFixtures.createSelfHostedRunnerGroupForEnterprisePayload();

        assertSoftly(softly -> {
            softly.assertThat(value.name()).isEqualTo("name-sample");
            softly.assertThat(value.visibility()).isEqualTo(GitHubRunnerGroupVisibilityForEnterprise.SELECTED);
            softly.assertThat(value.selectedOrganizationIds()).containsExactly(1L);
            softly.assertThat(value.runners()).containsExactly(1L);
            softly.assertThat(value.allowsPublicRepositories()).isTrue();
            softly.assertThat(value.restrictedToWorkflows()).isTrue();
            softly.assertThat(value.selectedWorkflows()).containsExactly("selectedWorkflows-sample");
            softly.assertThat(value.networkConfigurationId()).isEqualTo("networkConfigurationId-sample");
        });
    }
}
