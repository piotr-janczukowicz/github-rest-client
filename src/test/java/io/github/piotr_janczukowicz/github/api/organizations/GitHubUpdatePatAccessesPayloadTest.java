package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubUpdatePatAccessesPayloadTest {

    @Test
    void constructor_shouldCopyPatIds_whenPresent() {
        final GitHubUpdatePatAccessesPayload payload = GitHubUpdatePatAccessesPayloadBuilder.builder()
                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                .patIds(List.of(42L, 73L))
                .build();

        assertThat(payload.patIds()).containsExactly(42L, 73L);
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
                        (ConstructorCall) () -> GitHubUpdatePatAccessesPayloadBuilder.builder()
                                .patIds(List.of(1L))
                                .build(),
                        NullPointerException.class,
                        "action"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubUpdatePatAccessesPayloadBuilder.builder()
                                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                                .build(),
                        NullPointerException.class,
                        "patIds"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubUpdatePatAccessesPayloadBuilder.builder()
                                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                                .patIds(List.of())
                                .build(),
                        IllegalArgumentException.class,
                        "patIds"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubUpdatePatAccessesPayloadBuilder.builder()
                                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                                .patIds(tooManyPatIds())
                                .build(),
                        IllegalArgumentException.class,
                        "patIds"));
    }

    private static List<Long> tooManyPatIds() {
        return IntStream.rangeClosed(1, 101).mapToObj(i -> (long) i).toList();
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
