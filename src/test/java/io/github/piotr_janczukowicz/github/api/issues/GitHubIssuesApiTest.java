package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssuesApiTest {

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void artifacts_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubIssuesApi api = new GitHubIssuesApi(TestClients.caller(HttpTransportMocks.mock()));
        final GitHubIssuesArtifactsApi first = api.artifacts();

        final GitHubIssuesArtifactsApi second = api.artifacts();

        assertThat(second).isSameAs(first);
    }
}
