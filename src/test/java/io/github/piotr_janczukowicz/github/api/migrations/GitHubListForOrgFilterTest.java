package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForOrgFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListForOrgFilter filter = GitHubListForOrgFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeExclude_whenSet() {
        final GitHubListForOrgFilter filter = GitHubListForOrgFilterBuilder.builder()
                .exclude(List.of(GitHubMigrationExclude.REPOSITORIES))
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("exclude", "repositories");
    }

    @Test
    void constructor_shouldKeepEmptyExclude_whenExcludeEmpty() {
        final List<GitHubMigrationExclude> exclude = new ArrayList<>();

        final GitHubListForOrgFilter filter =
                GitHubListForOrgFilterBuilder.builder().exclude(exclude).build();
        exclude.add(GitHubMigrationExclude.REPOSITORIES);

        assertSoftly(softly -> {
            softly.assertThat(filter.exclude()).isEmpty();
            softly.assertThat(filter.toQueryParameters()).isEmpty();
        });
    }
}
