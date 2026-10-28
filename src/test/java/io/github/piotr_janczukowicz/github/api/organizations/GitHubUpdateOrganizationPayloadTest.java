package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationPayloadTest {

    @Test
    void constructor_shouldThrow_whenDescriptionTooLong() {
        final GitHubUpdateOrganizationPayloadBuilder builder =
                GitHubUpdateOrganizationPayloadBuilder.builder().description("x".repeat(161));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldAcceptDescription_whenDescriptionWithinMaxLength() {
        final GitHubUpdateOrganizationPayload payload = GitHubUpdateOrganizationPayloadBuilder.builder()
                .description("x".repeat(160))
                .build();

        assertThat(payload.description()).hasSize(160);
    }
}
