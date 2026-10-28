package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePatAccessPayloadTest {

    @Test
    void constructor_shouldAcceptPayload_whenActionPresent() {
        final GitHubUpdatePatAccessPayload payload = GitHubUpdatePatAccessPayloadBuilder.builder()
                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                .build();

        assertThat(payload.action()).isEqualTo(GitHubOrganizationPatAccessUpdateAction.REVOKE);
    }

    @Test
    void constructor_shouldThrow_whenActionNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdatePatAccessPayloadBuilder.builder().build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("action");
    }
}
