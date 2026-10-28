package io.github.piotr_janczukowicz.github.api.reactions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForReleaseFilterTest {

    @Test
    void empty_shouldReturnNullContent_whenCalled() {
        final GitHubListForReleaseFilter filter = GitHubListForReleaseFilter.empty();

        assertThat(filter.content()).isNull();
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenAllNull() {
        final Map<String, String> query = GitHubListForReleaseFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldPutContent_whenSet() {
        final GitHubListForReleaseFilter filter = GitHubListForReleaseFilterBuilder.builder()
                .content(GitHubReleaseReactionContent.HEART)
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("content", "heart");
    }
}
