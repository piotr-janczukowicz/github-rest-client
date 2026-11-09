package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.gists.GitHubBaseGist;
import io.github.piotr_janczukowicz.github.api.gists.GitHubCreateCommentPayload;
import io.github.piotr_janczukowicz.github.api.gists.GitHubCreateGistFile;
import io.github.piotr_janczukowicz.github.api.gists.GitHubCreatePayload;
import io.github.piotr_janczukowicz.github.api.gists.GitHubGistSimple;
import io.github.piotr_janczukowicz.github.api.gists.GitHubUpdateGistFile;
import io.github.piotr_janczukowicz.github.api.gists.GitHubUpdatePayload;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class GistsSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    private static GitHubGistSimple createSecretGist(final GitHub github, final String file, final String content)
            throws IOException {
        return github.gists()
                .gist()
                .createGist(new GitHubCreatePayload(
                        ENV.name("gist"), Map.of(file, new GitHubCreateGistFile(content)), false));
    }

    @Test
    void gist_shouldRoundTrip_whenCreatedUpdatedStarredAndDeleted() throws IOException {
        final GitHub github = ENV.github();
        final String file = ENV.name("file") + ".txt";
        final GitHubGistSimple created = createSecretGist(github, file, "one");
        try {
            github.gists()
                    .gist()
                    .updateGist(
                            created.id(),
                            new GitHubUpdatePayload(null, Map.of(file, new GitHubUpdateGistFile("two", null))));
            github.gists().gist().starGist(created.id());
            final boolean starred = github.gists().gist().isGistStarred(created.id());
            github.gists().gist().unstarGist(created.id());
            final boolean unstarred = github.gists().gist().isGistStarred(created.id());
            final GitHubGistSimple fetched =
                    github.gists().gist().getGist(created.id()).orElseThrow();

            assertThat(created.isPublic()).isFalse();
            assertThat(fetched.files().get(file).content()).isEqualTo("two");
            assertThat(starred).isTrue();
            assertThat(unstarred).isFalse();
        } finally {
            github.gists().gist().deleteGist(created.id());
        }
    }

    @Test
    void listGists_shouldContainTheGist_whenItWasJustCreated() throws IOException {
        final GitHub github = ENV.github();
        final GitHubGistSimple created = createSecretGist(github, ENV.name("list") + ".txt", "list");
        try {
            final boolean listed = github.gists().gist().listGists().all().stream()
                    .map(GitHubBaseGist::id)
                    .anyMatch(created.id()::equals);

            assertThat(listed).isTrue();
        } finally {
            github.gists().gist().deleteGist(created.id());
        }
    }

    @Test
    void getGist_shouldBeEmpty_whenTheGistWasDeleted() throws IOException {
        final GitHub github = ENV.github();
        final GitHubGistSimple created = createSecretGist(github, ENV.name("gone") + ".txt", "gone");
        github.gists().gist().deleteGist(created.id());

        final var gist = github.gists().gist().getGist(created.id());

        assertThat(gist).isEmpty();
    }

    @Test
    void comment_shouldRoundTrip_whenCreatedListedAndDeleted() throws IOException {
        final GitHub github = ENV.github();
        final GitHubGistSimple created = createSecretGist(github, ENV.name("comment") + ".txt", "comment");
        try {
            final var comment =
                    github.gists().comments().createComment(created.id(), new GitHubCreateCommentPayload("smoke"));
            final var listed =
                    github.gists().comments().listComments(created.id()).all();
            github.gists().comments().deleteComment(created.id(), comment.id());

            assertThat(comment.body()).isEqualTo("smoke");
            assertThat(listed).extracting(item -> item.id()).contains(comment.id());
        } finally {
            github.gists().gist().deleteGist(created.id());
        }
    }
}
