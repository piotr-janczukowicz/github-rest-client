package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryAdvisoryCreditPayloadTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubRepositoryAdvisoryCreditPayload(null, GitHubSecurityAdvisoryCreditType.FINDER));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryAdvisoryCreditPayload("octocat", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
