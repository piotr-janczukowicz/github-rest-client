package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubUpdateSecretScanningAlertPayloadTest {

    @Test
    void constructor_shouldThrow_whenAllOfStateAssigneeAndValidityNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubUpdateSecretScanningAlertPayload(null, null, null, null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("state, assignee or validity must have at least one value present");
    }

    @Test
    void constructor_shouldAcceptPayload_whenOnlyStatePresent() {
        final GitHubUpdateSecretScanningAlertPayload payload =
                new GitHubUpdateSecretScanningAlertPayload(GitHubSecretScanningAlertState.OPEN, null, null, null, null);

        assertThat(payload.state()).isEqualTo(GitHubSecretScanningAlertState.OPEN);
    }

    @Test
    void constructor_shouldAcceptPayload_whenOnlyAssigneePresent() {
        final GitHubUpdateSecretScanningAlertPayload payload =
                new GitHubUpdateSecretScanningAlertPayload(null, null, null, OptionalNull.of("hubot"), null);

        assertThat(payload.assignee().value()).isEqualTo("hubot");
    }

    @Test
    void constructor_shouldAcceptPayload_whenOnlyValidityPresent() {
        final GitHubUpdateSecretScanningAlertPayload payload = new GitHubUpdateSecretScanningAlertPayload(
                null, null, null, null, OptionalNull.of(GitHubSecretScanningAlertUpdateValidity.ACTIVE));

        assertThat(payload.validity().value()).isEqualTo(GitHubSecretScanningAlertUpdateValidity.ACTIVE);
    }

    @Test
    void constructor_shouldAcceptPayload_whenResolvedWithResolution() {
        final GitHubUpdateSecretScanningAlertPayload payload = new GitHubUpdateSecretScanningAlertPayload(
                GitHubSecretScanningAlertState.RESOLVED,
                GitHubSecretScanningAlertResolution.FALSE_POSITIVE,
                null,
                null,
                null);

        assertSoftly(softly -> {
            softly.assertThat(payload.state()).isEqualTo(GitHubSecretScanningAlertState.RESOLVED);
            softly.assertThat(payload.resolution()).isEqualTo(GitHubSecretScanningAlertResolution.FALSE_POSITIVE);
        });
    }

    @Test
    void constructor_shouldThrow_whenNoAnyOfField() {
        final GitHubUpdateSecretScanningAlertPayloadBuilder builder =
                GitHubUpdateSecretScanningAlertPayloadBuilder.builder();

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("state, assignee or validity must have at least one value present");
    }

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenResolvedWithoutResolution(final ConstructorCall call) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("resolution is required");
    }

    private static Stream<Arguments> invalidConstructors() {
        return Stream.of(Arguments.of((ConstructorCall) () -> new GitHubUpdateSecretScanningAlertPayload(
                GitHubSecretScanningAlertState.RESOLVED, null, null, null, null)));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
