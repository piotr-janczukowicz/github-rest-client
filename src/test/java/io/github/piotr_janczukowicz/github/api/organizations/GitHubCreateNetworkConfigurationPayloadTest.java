package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCreateNetworkConfigurationPayloadTest {

    @Test
    void constructor_shouldAcceptPayload_whenRequiredFieldsPresent() {
        final GitHubCreateNetworkConfigurationPayload payload =
                validPayloadBuilder().build();

        assertThat(payload.networkSettingsIds()).containsExactly("23456789ABDCEF1");
    }

    @Test
    void constructor_shouldAcceptOptionalFields_whenPresent() {
        final GitHubCreateNetworkConfigurationPayload payload = validPayloadBuilder()
                .computeService(GitHubCreateNetworkConfigurationComputeService.ACTIONS)
                .failoverNetworkSettingsIds(List.of("3456789ABDCEF12"))
                .failoverNetworkEnabled(true)
                .build();

        assertThat(payload.computeService()).isEqualTo(GitHubCreateNetworkConfigurationComputeService.ACTIONS);
    }

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenInvalid(
            final ConstructorCall call, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateNetworkConfigurationPayloadBuilder.builder()
                                .networkSettingsIds(List.of("23456789ABDCEF1"))
                                .build(),
                        NullPointerException.class,
                        "name"),
                Arguments.of(
                        (ConstructorCall) () ->
                                validPayloadBuilder().name("n".repeat(101)).build(),
                        IllegalArgumentException.class,
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubCreateNetworkConfigurationPayloadBuilder.builder()
                                .name("my-network-configuration")
                                .build(),
                        NullPointerException.class,
                        "networkSettingsIds"),
                Arguments.of(
                        (ConstructorCall) () -> validPayloadBuilder()
                                .networkSettingsIds(List.of())
                                .build(),
                        IllegalArgumentException.class,
                        "networkSettingsIds"),
                Arguments.of(
                        (ConstructorCall) () -> validPayloadBuilder()
                                .networkSettingsIds(List.of("id-1", "id-2"))
                                .build(),
                        IllegalArgumentException.class,
                        "networkSettingsIds"),
                Arguments.of(
                        (ConstructorCall) () -> validPayloadBuilder()
                                .failoverNetworkSettingsIds(List.of("id-1", "id-2"))
                                .build(),
                        IllegalArgumentException.class,
                        "failoverNetworkSettingsIds"));
    }

    private static GitHubCreateNetworkConfigurationPayloadBuilder validPayloadBuilder() {
        return GitHubCreateNetworkConfigurationPayloadBuilder.builder()
                .name("my-network-configuration")
                .networkSettingsIds(List.of("23456789ABDCEF1"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
