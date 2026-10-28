package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubAddPullRequestsToStackPayloadTest {

    @Test
    void constructor_shouldCopyList_whenValid() {
        final List<Integer> pullRequests = new ArrayList<>(List.of(104));

        final GitHubAddPullRequestsToStackPayload payload = new GitHubAddPullRequestsToStackPayload(pullRequests);
        pullRequests.add(105);

        assertThat(payload.pullRequests()).containsExactly(104);
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
                        (ConstructorCall) () -> new GitHubAddPullRequestsToStackPayload(null),
                        NullPointerException.class,
                        "pullRequests"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubAddPullRequestsToStackPayload(List.of()),
                        IllegalArgumentException.class,
                        "pullRequests"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubAddPullRequestsToStackPayload(Collections.nCopies(101, 1)),
                        IllegalArgumentException.class,
                        "between 1 and 100"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
