package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateSelfHostedRunnerGroupForOrgPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final GitHubRunnerGroupVisibility visibility = GitHubRunnerGroupVisibility.SELECTED;
        final List<Long> selectedRepositoryIds = List.of(1L);
        final List<Long> runners = List.of(1L);
        final Boolean allowsPublicRepositories = true;
        final Boolean restrictedToWorkflows = true;
        final List<String> selectedWorkflows = List.of("selectedWorkflows-sample");
        final String networkConfigurationId = "networkConfigurationId-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubCreateSelfHostedRunnerGroupForOrgPayload(
                null,
                visibility,
                selectedRepositoryIds,
                runners,
                allowsPublicRepositories,
                restrictedToWorkflows,
                selectedWorkflows,
                networkConfigurationId));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenSelectedRepositoryIdsGiven() {
        final List<Long> source = new ArrayList<>(List.of(1L));
        final int size = source.size();
        final GitHubCreateSelfHostedRunnerGroupForOrgPayload payload =
                GitHubCreateSelfHostedRunnerGroupForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload())
                        .selectedRepositoryIds(source)
                        .build();

        source.add(source.getFirst());
        final List<Long> selectedRepositoryIds = payload.selectedRepositoryIds();
        final Long first = source.getFirst();

        final Throwable thrown = catchThrowable(() -> selectedRepositoryIds.add(first));

        assertSoftly(softly -> {
            softly.assertThat(payload.selectedRepositoryIds()).hasSize(size);
            softly.assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldAcceptNullSelectedRepositoryIds_whenSelectedRepositoryIdsNull() {
        final GitHubCreateSelfHostedRunnerGroupForOrgPayload payload =
                GitHubCreateSelfHostedRunnerGroupForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload())
                        .selectedRepositoryIds(null)
                        .build();

        assertThat(payload.selectedRepositoryIds()).isNull();
    }

    @Test
    void constructor_shouldCopyRunners_whenRunnersGiven() {
        final List<Long> source = new ArrayList<>(ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload()
                .runners());
        final int size = source.size();
        final GitHubCreateSelfHostedRunnerGroupForOrgPayload payload =
                GitHubCreateSelfHostedRunnerGroupForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload())
                        .runners(source)
                        .build();

        source.add(source.getFirst());
        final List<Long> runners = payload.runners();
        final Long first = source.getFirst();

        final Throwable thrown = catchThrowable(() -> runners.add(first));

        assertSoftly(softly -> {
            softly.assertThat(payload.runners()).hasSize(size);
            softly.assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        });
    }

    @Test
    void constructor_shouldAcceptNullRunners_whenRunnersNull() {
        final GitHubCreateSelfHostedRunnerGroupForOrgPayload payload =
                GitHubCreateSelfHostedRunnerGroupForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload())
                        .runners(null)
                        .build();

        assertThat(payload.runners()).isNull();
    }

    @Test
    void constructor_shouldCopySelectedWorkflows_whenSelectedWorkflowsGiven() {
        final List<String> source = new ArrayList<>(ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload()
                .selectedWorkflows());
        final int size = source.size();
        final GitHubCreateSelfHostedRunnerGroupForOrgPayload payload =
                GitHubCreateSelfHostedRunnerGroupForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload())
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
        final GitHubCreateSelfHostedRunnerGroupForOrgPayload payload =
                GitHubCreateSelfHostedRunnerGroupForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload())
                        .selectedWorkflows(null)
                        .build();

        assertThat(payload.selectedWorkflows()).isNull();
    }
}
