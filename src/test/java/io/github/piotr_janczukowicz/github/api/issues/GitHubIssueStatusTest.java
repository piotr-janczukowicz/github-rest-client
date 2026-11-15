package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueStatusTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubIssueStatusBuilder builder = GitHubIssueStatusBuilder.builder(IssuesCatalogFixtures.issueStatus())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenCategoryNull() {
        final GitHubIssueStatusBuilder builder = GitHubIssueStatusBuilder.builder(IssuesCatalogFixtures.issueStatus())
                .category(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("category");
    }
}
