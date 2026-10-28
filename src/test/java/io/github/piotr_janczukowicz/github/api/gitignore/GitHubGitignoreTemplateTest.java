package io.github.piotr_janczukowicz.github.api.gitignore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitignoreTemplateTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubGitignoreTemplate valid = GitignoreCatalogFixtures.gitignoreTemplate();

        final Throwable thrown = catchThrowable(() -> new GitHubGitignoreTemplate(null, valid.source()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenSourceNull() {
        final GitHubGitignoreTemplate valid = GitignoreCatalogFixtures.gitignoreTemplate();

        final Throwable thrown = catchThrowable(() -> new GitHubGitignoreTemplate(valid.name(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("source");
    }
}
