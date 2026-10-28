package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetContentFilterTest {

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        final GitHubGetContentFilter filter = GitHubGetContentFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeRef_whenProvided() {
        final GitHubGetContentFilter filter =
                GitHubGetContentFilterBuilder.builder().ref("main").build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("ref", "main");
    }
}
