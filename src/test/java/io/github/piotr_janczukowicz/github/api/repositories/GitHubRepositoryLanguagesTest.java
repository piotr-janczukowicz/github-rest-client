package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryLanguagesTest {

    @Test
    void constructor_shouldRejectNull_whenLanguagesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryLanguages(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("languages");
    }

    @Test
    void fromJson_shouldWrapLanguages_whenMapProvided() {
        final GitHubRepositoryLanguages languages = GitHubRepositoryLanguages.fromJson(Map.of("Java", 42));

        assertThat(languages.languages()).containsEntry("Java", 42);
    }

    @Test
    void toJson_shouldReturnLanguages_whenCalled() {
        final GitHubRepositoryLanguages languages = new GitHubRepositoryLanguages(Map.of("Go", 10));

        assertThat(languages.toJson()).containsEntry("Go", 10);
    }
}
