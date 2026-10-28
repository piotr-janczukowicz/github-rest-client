package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryTopicsTest {

    @Test
    void constructor_shouldThrow_whenNamesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryTopics(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("names");
    }

    @Test
    void constructor_shouldCopyNames_whenNamesProvided() {
        final GitHubRepositoryTopics topics = GitHubRepositoryTopicsBuilder.builder()
                .names(List.of("api", "java"))
                .build();

        assertThat(topics.names()).containsExactly("api", "java");
    }
}
