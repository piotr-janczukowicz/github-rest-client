package io.github.piotr_janczukowicz.github.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningFirstDetectedLocation;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationActionsLog;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationCommit;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationDiscussionBody;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationDiscussionComment;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationDiscussionTitle;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationIssueBody;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationIssueComment;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationIssueTitle;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationPullRequestBody;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationPullRequestComment;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationPullRequestReview;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationPullRequestReviewComment;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationPullRequestTitle;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationWikiCommit;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningUnknownLocationDetails;
import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubSecretScanningFirstDetectedLocationDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnUnknown_whenShapeMatchesNoSubtype() {
        final byte[] json = "{\"some_new_url\":\"https://example.test/x\",\"line\":3}".getBytes(StandardCharsets.UTF_8);

        final GitHubSecretScanningFirstDetectedLocation decoded =
                CODEC.decode(json, GitHubSecretScanningFirstDetectedLocation.class);

        assertThat(decoded)
                .isEqualTo(new GitHubSecretScanningUnknownLocationDetails(
                        Map.of("some_new_url", "https://example.test/x", "line", 3)));
    }

    @Test
    void decode_shouldReturnCommit_whenCommitShape() {
        final byte[] json = FileUtils.readBytes(
                GitHubSecretScanningLocationCommit.class, "secret-scanning-location-commit-component.json");

        final GitHubSecretScanningFirstDetectedLocation decoded =
                CODEC.decode(json, GitHubSecretScanningFirstDetectedLocation.class);

        assertThat(decoded).isInstanceOf(GitHubSecretScanningLocationCommit.class);
    }

    @Test
    void decode_shouldReturnUnknown_whenOnlyHtmlUrlPresent() {
        final byte[] json =
                "{\"html_url\":\"https://github.com/octocat/Hello-World\"}".getBytes(StandardCharsets.UTF_8);

        final GitHubSecretScanningFirstDetectedLocation decoded =
                CODEC.decode(json, GitHubSecretScanningFirstDetectedLocation.class);

        assertThat(decoded)
                .isEqualTo(new GitHubSecretScanningUnknownLocationDetails(
                        Map.of("html_url", "https://github.com/octocat/Hello-World")));
    }

    @ParameterizedTest
    @MethodSource("knownShapes")
    void decode_shouldReturnRecord_whenShapeIsKnown(
            final String fixture, final Class<? extends GitHubSecretScanningFirstDetectedLocation> type) {
        final byte[] json = FileUtils.readBytes(GitHubSecretScanningLocationCommit.class, fixture);

        final GitHubSecretScanningFirstDetectedLocation decoded =
                CODEC.decode(json, GitHubSecretScanningFirstDetectedLocation.class);

        assertThat(decoded).isInstanceOf(type);
    }

    @Test
    void decode_shouldReturnUnknown_whenActionsLogShapeIsIncomplete() {
        final byte[] json = "{\"path\":\"a.yml\",\"start_line\":1}".getBytes(StandardCharsets.UTF_8);

        final GitHubSecretScanningFirstDetectedLocation decoded =
                CODEC.decode(json, GitHubSecretScanningFirstDetectedLocation.class);

        assertThat(decoded).isInstanceOf(GitHubSecretScanningUnknownLocationDetails.class);
    }

    private static Stream<Arguments> knownShapes() {
        return Stream.of(
                Arguments.of(
                        "secret-scanning-location-commit-component.json", GitHubSecretScanningLocationCommit.class),
                Arguments.of(
                        "secret-scanning-location-wiki-commit-details.json",
                        GitHubSecretScanningLocationWikiCommit.class),
                Arguments.of(
                        "secret-scanning-location-issue-title-details.json",
                        GitHubSecretScanningLocationIssueTitle.class),
                Arguments.of(
                        "secret-scanning-location-issue-body-details.json",
                        GitHubSecretScanningLocationIssueBody.class),
                Arguments.of(
                        "secret-scanning-location-issue-comment-details.json",
                        GitHubSecretScanningLocationIssueComment.class),
                Arguments.of(
                        "secret-scanning-location-discussion-title-details.json",
                        GitHubSecretScanningLocationDiscussionTitle.class),
                Arguments.of(
                        "secret-scanning-location-discussion-body-details.json",
                        GitHubSecretScanningLocationDiscussionBody.class),
                Arguments.of(
                        "secret-scanning-location-discussion-comment-details.json",
                        GitHubSecretScanningLocationDiscussionComment.class),
                Arguments.of(
                        "secret-scanning-location-pull-request-title-details.json",
                        GitHubSecretScanningLocationPullRequestTitle.class),
                Arguments.of(
                        "secret-scanning-location-pull-request-body-details.json",
                        GitHubSecretScanningLocationPullRequestBody.class),
                Arguments.of(
                        "secret-scanning-location-pull-request-comment-details.json",
                        GitHubSecretScanningLocationPullRequestComment.class),
                Arguments.of(
                        "secret-scanning-location-pull-request-review-details.json",
                        GitHubSecretScanningLocationPullRequestReview.class),
                Arguments.of(
                        "secret-scanning-location-pull-request-review-comment-details.json",
                        GitHubSecretScanningLocationPullRequestReviewComment.class),
                Arguments.of(
                        "secret-scanning-location-actions-log-details.json",
                        GitHubSecretScanningLocationActionsLog.class));
    }
}
