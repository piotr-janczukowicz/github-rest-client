package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubRepositoryActivityTypeTest {

    @ParameterizedTest
    @CsvSource({
        "push,PUSH",
        "force_push,FORCE_PUSH",
        "branch_creation,BRANCH_CREATION",
        "branch_deletion,BRANCH_DELETION",
        "pr_merge,PR_MERGE",
        "merge_queue_merge,MERGE_QUEUE_MERGE"
    })
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubRepositoryActivityType value) {
        final GitHubRepositoryActivityType decoded = GitHubRepositoryActivityType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubRepositoryActivityType.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
