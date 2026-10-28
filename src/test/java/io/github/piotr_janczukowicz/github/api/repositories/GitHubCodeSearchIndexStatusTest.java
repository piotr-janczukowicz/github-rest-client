package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeSearchIndexStatusTest {

    @Test
    void constructor_shouldRetainFields_whenProvided() {
        final GitHubCodeSearchIndexStatus status = new GitHubCodeSearchIndexStatus(true, "abc123def456");

        assertSoftly(softly -> {
            softly.assertThat(status).hasNoNullFieldsOrProperties();
            softly.assertThat(status.lexicalSearchOk()).isTrue();
            softly.assertThat(status.lexicalCommitSha()).isEqualTo("abc123def456");
        });
    }
}
