package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAgentMergeEnrollmentResponseTest {

    @Test
    void constructor_shouldThrow_whenAllowedActionsNull() {
        final GitHubGetAgentMergeEnrollmentResponseBuilder builder =
                GitHubGetAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.getAgentMergeEnrollmentResponse())
                        .allowedActions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("allowedActions");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubGetAgentMergeEnrollmentResponseBuilder builder =
                GitHubGetAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.getAgentMergeEnrollmentResponse())
                        .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubGetAgentMergeEnrollmentResponseBuilder builder =
                GitHubGetAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.getAgentMergeEnrollmentResponse())
                        .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final GitHubGetAgentMergeEnrollmentResponseBuilder builder =
                GitHubGetAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.getAgentMergeEnrollmentResponse())
                        .user(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenAllowedActionsTooSmall() {
        final GitHubGetAgentMergeEnrollmentResponseBuilder builder =
                GitHubGetAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.getAgentMergeEnrollmentResponse())
                        .allowedActions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("allowedActions");
    }

    @Test
    void constructor_shouldAccept_whenAllowedActionsRepeat() {
        final GitHubGetAgentMergeEnrollmentResponseBuilder builder =
                GitHubGetAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.getAgentMergeEnrollmentResponse())
                        .allowedActions(List.of(
                                GitHubGetAgentMergeEnrollmentResponseAllowedAction.FIX_CI,
                                GitHubGetAgentMergeEnrollmentResponseAllowedAction.FIX_CI));

        final GitHubGetAgentMergeEnrollmentResponse value = builder.build();

        assertThat(value.allowedActions()).hasSize(2);
    }
}
