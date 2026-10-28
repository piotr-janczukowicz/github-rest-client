package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubReviewPatGrantRequestsInBulkPayloadTest {

    @Test
    void constructor_shouldKeepNullPatRequestIds_whenAbsent() {
        final GitHubReviewPatGrantRequestsInBulkPayload payload =
                GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder()
                        .action(GitHubOrganizationPatGrantReviewAction.APPROVE)
                        .build();

        assertThat(payload.patRequestIds()).isNull();
    }

    @Test
    void constructor_shouldCopyPatRequestIds_whenPresent() {
        final GitHubReviewPatGrantRequestsInBulkPayload payload =
                GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder()
                        .action(GitHubOrganizationPatGrantReviewAction.DENY)
                        .patRequestIds(List.of(42L, 73L))
                        .build();

        assertThat(payload.patRequestIds()).containsExactly(42L, 73L);
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
                        (ConstructorCall) () -> GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder()
                                .patRequestIds(List.of(1L))
                                .build(),
                        NullPointerException.class,
                        "action"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder()
                                .action(GitHubOrganizationPatGrantReviewAction.DENY)
                                .patRequestIds(List.of())
                                .build(),
                        IllegalArgumentException.class,
                        "patRequestIds"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder()
                                .action(GitHubOrganizationPatGrantReviewAction.DENY)
                                .patRequestIds(tooManyPatRequestIds())
                                .build(),
                        IllegalArgumentException.class,
                        "patRequestIds"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder()
                                .action(GitHubOrganizationPatGrantReviewAction.DENY)
                                .reason(OptionalNull.of("x".repeat(1025)))
                                .build(),
                        IllegalArgumentException.class,
                        "reason"));
    }

    private static List<Long> tooManyPatRequestIds() {
        return IntStream.rangeClosed(1, 101).mapToObj(i -> (long) i).toList();
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
