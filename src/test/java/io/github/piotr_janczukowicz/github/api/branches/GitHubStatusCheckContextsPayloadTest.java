package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubStatusCheckContextsPayloadTest {

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubStatusCheckContextsPayload(List.of("ci"), List.of("ci")));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of contexts or contextList is required");
    }

    @Test
    void constructor_shouldThrow_whenNeitherBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubStatusCheckContextsPayload(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of contexts or contextList is required");
    }

    @Test
    void constructor_shouldAcceptEmptyContexts_whenContextsBranch() {
        final GitHubStatusCheckContextsPayload payload = GitHubStatusCheckContextsPayload.withContexts(List.of());

        assertThat(payload.contexts()).isEmpty();
    }

    @Test
    void constructor_shouldAcceptEmptyContextList_whenContextListBranch() {
        final GitHubStatusCheckContextsPayload payload = GitHubStatusCheckContextsPayload.withContextList(List.of());

        assertThat(payload.contextList()).isEmpty();
    }
}
