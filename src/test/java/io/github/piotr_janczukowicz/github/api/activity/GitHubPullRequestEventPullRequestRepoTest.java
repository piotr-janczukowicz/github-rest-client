package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestEventPullRequestRepoTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubPullRequestEventPullRequestRepoBuilder builder =
                GitHubPullRequestEventPullRequestRepoBuilder.builder(
                                ActivityCatalogFixtures.pullRequestEventPullRequestRepo())
                        .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubPullRequestEventPullRequestRepoBuilder builder =
                GitHubPullRequestEventPullRequestRepoBuilder.builder(
                                ActivityCatalogFixtures.pullRequestEventPullRequestRepo())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void toString_shouldOmitTheCloneToken_whenRepositoryHasOne() {
        final GitHubPullRequestEventPullRequestRepo repository =
                ActivityCatalogFixtures.pullRequestEventPullRequestRepo();

        final String text = repository.toString();

        assertSoftly(softly -> {
            softly.assertThat(repository.tempCloneToken()).isNotNull();
            softly.assertThat(text).contains("id=" + repository.id()).contains(repository.name());
            softly.assertThat(text).doesNotContain(repository.tempCloneToken());
        });
    }
}
