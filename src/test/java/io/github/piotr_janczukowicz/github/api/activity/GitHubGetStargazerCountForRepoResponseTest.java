package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetStargazerCountForRepoResponseTest {

    @Test
    void constructor_shouldThrow_whenCountNegative() {
        final Throwable thrown = catchThrowable(() -> new GitHubGetStargazerCountForRepoResponse(-1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("count");
    }

    @Test
    void constructor_shouldAcceptZero_whenCountZero() {
        final GitHubGetStargazerCountForRepoResponse response = new GitHubGetStargazerCountForRepoResponse(0);

        assertThat(response.count()).isZero();
    }
}
