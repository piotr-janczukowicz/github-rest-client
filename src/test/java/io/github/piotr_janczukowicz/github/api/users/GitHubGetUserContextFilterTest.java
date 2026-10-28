package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetUserContextFilterTest {

    @Test
    void constructor_shouldThrow_whenOnlySubjectTypeSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubGetUserContextFilter(GitHubUserContextSubjectType.ISSUE, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("subject");
    }

    @Test
    void constructor_shouldThrow_whenOnlySubjectIdSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubGetUserContextFilter(null, "123"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("subject");
    }

    @Test
    void constructor_shouldThrow_whenSubjectIdBlankAndTypeSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubGetUserContextFilter(GitHubUserContextSubjectType.ISSUE, " "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("subject");
    }

    @Test
    void toQueryParameters_shouldEmitStrippedSubjectId_whenWhitespacePadded() {
        final GitHubGetUserContextFilter filter =
                new GitHubGetUserContextFilter(GitHubUserContextSubjectType.REPOSITORY, " 123 ");

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("subject_type", "repository").containsEntry("subject_id", "123");
    }

    @Test
    void toQueryParameters_shouldEmitBoth_whenComplete() {
        final GitHubGetUserContextFilter filter =
                new GitHubGetUserContextFilter(GitHubUserContextSubjectType.REPOSITORY, "123");

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("subject_type", "repository").containsEntry("subject_id", "123");
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        final GitHubGetUserContextFilter filter = GitHubGetUserContextFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }
}
