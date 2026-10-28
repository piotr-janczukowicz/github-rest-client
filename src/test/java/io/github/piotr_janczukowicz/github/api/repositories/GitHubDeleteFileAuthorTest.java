package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeleteFileAuthorTest {

    @Test
    void constructor_shouldAllowNullNameAndEmail_whenCalled() {
        final GitHubDeleteFileAuthor author = new GitHubDeleteFileAuthor(null, null);

        assertSoftly(softly -> {
            softly.assertThat(author.name()).isNull();
            softly.assertThat(author.email()).isNull();
        });
    }

    @Test
    void constructor_shouldAllowPartialFields_whenCalled() {
        final GitHubDeleteFileAuthor author = new GitHubDeleteFileAuthor("Monalisa Octocat", null);

        assertSoftly(softly -> {
            softly.assertThat(author.name()).isEqualTo("Monalisa Octocat");
            softly.assertThat(author.email()).isNull();
        });
    }
}
