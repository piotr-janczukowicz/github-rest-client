package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPutAgentMergeEnrollmentResponseTest {

    @Test
    void constructor_shouldThrow_whenAllowedActionsNull() {
        final GitHubPutAgentMergeEnrollmentResponseBuilder builder =
                GitHubPutAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse())
                        .allowedActions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("allowedActions");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubPutAgentMergeEnrollmentResponseBuilder builder =
                GitHubPutAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse())
                        .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubPutAgentMergeEnrollmentResponseBuilder builder =
                GitHubPutAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse())
                        .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final GitHubPutAgentMergeEnrollmentResponseBuilder builder =
                GitHubPutAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse())
                        .user(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenAllowedActionsTooSmall() {
        final GitHubPutAgentMergeEnrollmentResponseBuilder builder =
                GitHubPutAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse())
                        .allowedActions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("allowedActions");
    }

    @Test
    void constructor_shouldAccept_whenAllowedActionsRepeat() {
        final GitHubPutAgentMergeEnrollmentResponseBuilder builder =
                GitHubPutAgentMergeEnrollmentResponseBuilder.builder(
                                PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse())
                        .allowedActions(List.of(
                                GitHubPutAgentMergeEnrollmentResponseAllowedAction.FIX_CI,
                                GitHubPutAgentMergeEnrollmentResponseAllowedAction.FIX_CI));

        final GitHubPutAgentMergeEnrollmentResponse value = builder.build();

        assertThat(value.allowedActions()).hasSize(2);
    }
}
