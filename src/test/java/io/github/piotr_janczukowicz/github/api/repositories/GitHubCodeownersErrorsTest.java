package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeownersErrorsTest {

    @Test
    void compactConstructor_shouldThrow_whenErrorsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeownersErrors(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("errors");
    }

    @Test
    void compactConstructor_shouldCopyErrors_whenErrorsProvided() {
        final GitHubCodeownersErrors errors =
                new GitHubCodeownersErrors(List.of(RepositoriesCatalogFixtures.codeownersError()));

        assertThat(errors.errors())
                .singleElement()
                .extracting(GitHubCodeownersError::line)
                .isEqualTo(7);
    }
}
