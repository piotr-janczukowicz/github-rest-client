package io.github.piotr_janczukowicz.github.api.commits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCombinedCommitStatusTest {

    @Test
    void constructor_shouldThrow_whenStatusesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCombinedCommitStatus(
                "success",
                null,
                "sha",
                0,
                RepositoriesCatalogFixtures.helloWorldMinimalRepository(),
                "https://example.com/commit",
                "https://example.com/status"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("statuses");
    }
}
