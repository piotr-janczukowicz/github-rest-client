package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPutAgentMergeEnrollmentPayloadTest {

    @Test
    void constructor_shouldThrow_whenAllowedActionsNull() {
        final GitHubPutAgentMergeEnrollmentPayloadBuilder builder = GitHubPutAgentMergeEnrollmentPayloadBuilder.builder(
                        PullrequestsCatalogFixtures.putAgentMergeEnrollmentPayload())
                .allowedActions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("allowedActions");
    }

    @Test
    void constructor_shouldThrow_whenAllowedActionsTooSmall() {
        final GitHubPutAgentMergeEnrollmentPayloadBuilder builder = GitHubPutAgentMergeEnrollmentPayloadBuilder.builder(
                        PullrequestsCatalogFixtures.putAgentMergeEnrollmentPayload())
                .allowedActions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("allowedActions");
    }

    @Test
    void constructor_shouldThrow_whenAllowedActionsDuplicated() {
        final GitHubPutAgentMergeEnrollmentPayloadBuilder builder = GitHubPutAgentMergeEnrollmentPayloadBuilder.builder(
                        PullrequestsCatalogFixtures.putAgentMergeEnrollmentPayload())
                .allowedActions(List.of(
                        GitHubPutAgentMergeEnrollmentAllowedAction.FIX_CI,
                        GitHubPutAgentMergeEnrollmentAllowedAction.FIX_CI));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("allowedActions");
    }
}
