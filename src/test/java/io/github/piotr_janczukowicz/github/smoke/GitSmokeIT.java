package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubCreateBlobPayload;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubCreateCommitPayload;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubCreateRefPayload;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubCreateTreeEntry;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubCreateTreeEntryMode;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubCreateTreeEntryType;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubCreateTreePayload;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubGitCommit;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubGitRef;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubGitTree;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubShortBlob;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubCreatePullRequestPayload;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequest;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestUpdateState;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubUpdatePullRequestPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentFile;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentResult;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCreateOrUpdateFileContentsPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubDeleteFilePayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubFileCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubGetContentFilter;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class GitSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    private static String defaultBranch(final GitHub github) throws IOException {
        return github.repositories()
                .repository()
                .get(ENV.owner(), ENV.repo())
                .orElseThrow()
                .defaultBranch();
    }

    private static String headSha(final GitHub github, final String branch) throws IOException {
        return github.gitDatabase()
                .refs()
                .getRef(ENV.owner(), ENV.repo(), "heads/" + branch)
                .orElseThrow()
                .object()
                .sha();
    }

    private static String encode(final String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    private static GitHubGitRef createBranch(final GitHub github, final String branch, final String sha)
            throws IOException {
        return github.gitDatabase()
                .refs()
                .createRef(ENV.owner(), ENV.repo(), new GitHubCreateRefPayload("refs/heads/" + branch, sha));
    }

    private static void deleteBranch(final GitHub github, final String branch) throws IOException {
        github.gitDatabase().refs().deleteRef(ENV.owner(), ENV.repo(), "heads/" + branch);
    }

    @Test
    void gitData_shouldRoundTrip_whenBlobTreeCommitAndRefAreCreated() throws IOException {
        final GitHub github = ENV.github();
        final String owner = ENV.owner();
        final String repo = ENV.repo();
        final String parent = headSha(github, defaultBranch(github));
        final String branch = ENV.name("git");
        final GitHubShortBlob blob =
                github.gitDatabase().blobs().createBlob(owner, repo, new GitHubCreateBlobPayload("smoke", "utf-8"));
        final GitHubCreateTreeEntry entry = new GitHubCreateTreeEntry(
                "smoke/" + ENV.runId() + ".txt",
                GitHubCreateTreeEntryMode.FILE,
                GitHubCreateTreeEntryType.BLOB,
                OptionalNull.of(blob.sha()),
                null);
        final GitHubGitTree tree = github.gitDatabase()
                .trees()
                .createTree(owner, repo, new GitHubCreateTreePayload(List.of(entry), parent));
        final GitHubGitCommit commit = github.gitDatabase()
                .commits()
                .createCommit(
                        owner,
                        repo,
                        new GitHubCreateCommitPayload("smoke commit", tree.sha(), List.of(parent), null, null, null));
        final GitHubGitRef ref = createBranch(github, branch, commit.sha());
        try {
            final GitHubGitCommit fetched = github.gitDatabase()
                    .commits()
                    .getCommit(owner, repo, commit.sha())
                    .orElseThrow();

            assertThat(ref.object().sha()).isEqualTo(commit.sha());
            assertThat(fetched.message()).isEqualTo("smoke commit");
        } finally {
            deleteBranch(github, branch);
        }
    }

    @Test
    void contents_shouldRoundTrip_whenFileIsCreatedUpdatedAndDeleted() throws IOException {
        final GitHub github = ENV.github();
        final String owner = ENV.owner();
        final String repo = ENV.repo();
        final String branch = ENV.name("contents");
        final String path = "smoke/" + ENV.runId() + "-contents.txt";
        createBranch(github, branch, headSha(github, defaultBranch(github)));
        try {
            final GitHubFileCommit created = github.repositories()
                    .contents()
                    .createOrUpdateFileContents(
                            owner,
                            repo,
                            path,
                            new GitHubCreateOrUpdateFileContentsPayload(
                                    "smoke create", encode("first"), null, branch, null, null));
            final GitHubFileCommit updated = github.repositories()
                    .contents()
                    .createOrUpdateFileContents(
                            owner,
                            repo,
                            path,
                            new GitHubCreateOrUpdateFileContentsPayload(
                                    "smoke update",
                                    encode("second"),
                                    created.content().sha(),
                                    branch,
                                    null,
                                    null));
            final GitHubGetContentFilter onBranch = new GitHubGetContentFilter(branch);
            final Optional<GitHubContentResult> content =
                    github.repositories().contents().getContent(owner, repo, path, onBranch);
            github.repositories()
                    .contents()
                    .deleteFile(
                            owner,
                            repo,
                            path,
                            new GitHubDeleteFilePayload(
                                    "smoke delete", updated.content().sha(), branch, null, null));
            final Optional<GitHubContentResult> afterDelete =
                    github.repositories().contents().getContent(owner, repo, path, onBranch);

            assertThat(content).containsInstanceOf(GitHubContentFile.class);
            assertThat(afterDelete).isEmpty();
        } finally {
            deleteBranch(github, branch);
        }
    }

    @Test
    void pullRequest_shouldOpenAndClose_whenABranchHasACommit() throws IOException {
        final GitHub github = ENV.github();
        final String owner = ENV.owner();
        final String repo = ENV.repo();
        final String base = defaultBranch(github);
        final String branch = ENV.name("pr");
        createBranch(github, branch, headSha(github, base));
        try {
            github.repositories()
                    .contents()
                    .createOrUpdateFileContents(
                            owner,
                            repo,
                            "smoke/" + ENV.runId() + "-pr.txt",
                            new GitHubCreateOrUpdateFileContentsPayload(
                                    "smoke pr", encode("pr"), null, branch, null, null));
            final GitHubPullRequest opened = github.pullRequests()
                    .pullRequest()
                    .create(
                            owner,
                            repo,
                            new GitHubCreatePullRequestPayload(
                                    ENV.name("pr"), branch, null, base, "smoke", null, null, null));
            final GitHubPullRequest closed = github.pullRequests()
                    .pullRequest()
                    .update(
                            owner,
                            repo,
                            opened.number(),
                            new GitHubUpdatePullRequestPayload(
                                    null, null, GitHubPullRequestUpdateState.CLOSED, null, null));

            assertThat(opened.state()).isEqualTo(GitHubPullRequestUpdateState.OPEN);
            assertThat(closed.state()).isEqualTo(GitHubPullRequestUpdateState.CLOSED);
        } finally {
            deleteBranch(github, branch);
        }
    }
}
