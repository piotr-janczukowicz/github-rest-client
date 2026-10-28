package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewPatGrantRequestPayloadTest {

    @Test
    void constructor_shouldAcceptPayload_whenActionPresent() {
        final GitHubReviewPatGrantRequestPayload payload = GitHubReviewPatGrantRequestPayloadBuilder.builder()
                .action(GitHubOrganizationPatGrantReviewAction.APPROVE)
                .reason(OptionalNull.of("Approved."))
                .build();

        assertThat(payload.reason().value()).isEqualTo("Approved.");
    }

    @Test
    void constructor_shouldThrow_whenActionNull() {
        final Throwable thrown = catchThrowable(() -> GitHubReviewPatGrantRequestPayloadBuilder.builder()
                .reason(OptionalNull.of("Denied."))
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("action");
    }

    @Test
    void constructor_shouldThrow_whenReasonExceedsMaxLength() {
        final Throwable thrown = catchThrowable(() -> GitHubReviewPatGrantRequestPayloadBuilder.builder()
                .action(GitHubOrganizationPatGrantReviewAction.DENY)
                .reason(OptionalNull.of("x".repeat(1025)))
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reason");
    }
}
