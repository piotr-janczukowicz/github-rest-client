package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetStatusForAuthenticatedUserFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubGetStatusForAuthenticatedUserFilter filter = GitHubGetStatusForAuthenticatedUserFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeExclude_whenSet() {
        final GitHubGetStatusForAuthenticatedUserFilter filter =
                GitHubGetStatusForAuthenticatedUserFilterBuilder.builder()
                        .exclude(List.of("repositories"))
                        .build();

        assertThat(filter.toQueryParameters()).containsEntry("exclude", "repositories");
    }

    @Test
    void constructor_shouldKeepEmptyExclude_whenExcludeEmpty() {
        final List<String> exclude = new ArrayList<>();

        final GitHubGetStatusForAuthenticatedUserFilter filter =
                GitHubGetStatusForAuthenticatedUserFilterBuilder.builder()
                        .exclude(exclude)
                        .build();
        exclude.add("repositories");

        assertSoftly(softly -> {
            softly.assertThat(filter.exclude()).isEmpty();
            softly.assertThat(filter.toQueryParameters()).isEmpty();
        });
    }
}
