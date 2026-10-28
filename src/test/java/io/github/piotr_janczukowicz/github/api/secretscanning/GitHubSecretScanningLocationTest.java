package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubSecretScanningLocationTest {

    @ParameterizedTest
    @MethodSource("nullRequiredCases")
    void constructor_shouldThrow_whenRequiredFieldNull(final Runnable construction, final String field) {
        final Throwable thrown = catchThrowable(construction::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(field);
    }

    private static Stream<Arguments> nullRequiredCases() {
        return Stream.of(
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationCommit(
                                null, 1, 1, 1, 1, "sha", "blob", "csha", "curl", null),
                        "path"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationWikiCommit(
                                null, 1, 1, 1, 1, "sha", "page", "csha", "curl"),
                        "path"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationDiscussionBody(null), "discussionBodyUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationDiscussionComment(null),
                        "discussionCommentUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationDiscussionTitle(null), "discussionTitleUrl"),
                Arguments.of((Runnable) () -> new GitHubSecretScanningLocationIssueTitle(null, null), "issueTitleUrl"),
                Arguments.of((Runnable) () -> new GitHubSecretScanningLocationIssueBody(null, null), "issueBodyUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationIssueComment(null, null), "issueCommentUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationPullRequestTitle(null, null),
                        "pullRequestTitleUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationPullRequestBody(null, null),
                        "pullRequestBodyUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationPullRequestComment(null, null),
                        "pullRequestCommentUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationPullRequestReview(null, null),
                        "pullRequestReviewUrl"),
                Arguments.of(
                        (Runnable) () -> new GitHubSecretScanningLocationPullRequestReviewComment(null, null),
                        "pullRequestReviewCommentUrl"));
    }

    @ParameterizedTest
    @MethodSource("htmlUrlLocationFactories")
    void constructor_shouldAcceptHtmlUrl_whenNullOrPresent(final HtmlUrlLocationFactory factory) {
        final String apiUrl = "https://api.github.com/resource";
        final String htmlUrl = "https://github.com/resource";

        assertSoftly(softly -> {
            softly.assertThat(htmlUrl(factory.build(apiUrl, null))).isNull();
            softly.assertThat(htmlUrl(factory.build(apiUrl, htmlUrl))).isEqualTo(htmlUrl);
        });
    }

    @ParameterizedTest
    @MethodSource("validSingleUrlLocations")
    void constructor_shouldAcceptRequiredUrl_whenValid(final SingleUrlLocationFactory factory) {
        final String url = "https://api.github.com/resource";

        assertThat(requiredUrl(factory.build(url))).isEqualTo(url);
    }

    private static Stream<Arguments> htmlUrlLocationFactories() {
        return Stream.of(
                Arguments.of((HtmlUrlLocationFactory) GitHubSecretScanningLocationIssueTitle::new),
                Arguments.of((HtmlUrlLocationFactory) GitHubSecretScanningLocationIssueBody::new),
                Arguments.of((HtmlUrlLocationFactory) GitHubSecretScanningLocationIssueComment::new),
                Arguments.of((HtmlUrlLocationFactory) GitHubSecretScanningLocationPullRequestTitle::new),
                Arguments.of((HtmlUrlLocationFactory) GitHubSecretScanningLocationPullRequestBody::new),
                Arguments.of((HtmlUrlLocationFactory) GitHubSecretScanningLocationPullRequestComment::new),
                Arguments.of((HtmlUrlLocationFactory) GitHubSecretScanningLocationPullRequestReview::new),
                Arguments.of((HtmlUrlLocationFactory) GitHubSecretScanningLocationPullRequestReviewComment::new));
    }

    private static Stream<Arguments> validSingleUrlLocations() {
        return Stream.of(
                Arguments.of((SingleUrlLocationFactory) GitHubSecretScanningLocationDiscussionBody::new),
                Arguments.of((SingleUrlLocationFactory) GitHubSecretScanningLocationDiscussionComment::new),
                Arguments.of((SingleUrlLocationFactory) GitHubSecretScanningLocationDiscussionTitle::new),
                Arguments.of((SingleUrlLocationFactory) url -> new GitHubSecretScanningLocationWikiCommit(
                        "wiki/page.md", 1, 1, 1, 1, "blob", url, "commit", url)));
    }

    private static String htmlUrl(final Object location) {
        return switch (location) {
            case final GitHubSecretScanningLocationIssueTitle value -> value.htmlUrl();
            case final GitHubSecretScanningLocationIssueBody value -> value.htmlUrl();
            case final GitHubSecretScanningLocationIssueComment value -> value.htmlUrl();
            case final GitHubSecretScanningLocationPullRequestTitle value -> value.htmlUrl();
            case final GitHubSecretScanningLocationPullRequestBody value -> value.htmlUrl();
            case final GitHubSecretScanningLocationPullRequestComment value -> value.htmlUrl();
            case final GitHubSecretScanningLocationPullRequestReview value -> value.htmlUrl();
            case final GitHubSecretScanningLocationPullRequestReviewComment value -> value.htmlUrl();
            default -> throw new IllegalArgumentException("unexpected location type");
        };
    }

    private static String requiredUrl(final Object location) {
        return switch (location) {
            case final GitHubSecretScanningLocationDiscussionBody value -> value.discussionBodyUrl();
            case final GitHubSecretScanningLocationDiscussionComment value -> value.discussionCommentUrl();
            case final GitHubSecretScanningLocationDiscussionTitle value -> value.discussionTitleUrl();
            case final GitHubSecretScanningLocationWikiCommit value -> value.pageUrl();
            default -> throw new IllegalArgumentException("unexpected location type");
        };
    }

    @FunctionalInterface
    private interface HtmlUrlLocationFactory {
        Object build(String apiUrl, String htmlUrl);
    }

    @FunctionalInterface
    private interface SingleUrlLocationFactory {
        Object build(String url);
    }
}
