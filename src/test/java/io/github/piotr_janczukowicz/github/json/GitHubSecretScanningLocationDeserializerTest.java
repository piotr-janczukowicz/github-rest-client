package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocation;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationCommit;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationDetails;
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
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationType;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationWikiCommit;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningUnknownLocationDetails;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubSecretScanningLocationDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @ParameterizedTest
    @EnumSource(value = GitHubSecretScanningLocationType.class, mode = EnumSource.Mode.EXCLUDE, names = "UNKNOWN")
    void deserialize_shouldMapDetails_whenTypeKnown(final GitHubSecretScanningLocationType type) {
        final byte[] json = FileUtils.readBytes(
                GitHubSecretScanningLocation.class,
                "secret-scanning-location-" + type.wireValue().replace('_', '-') + ".json");

        final GitHubSecretScanningLocation decoded = CODEC.decode(json, GitHubSecretScanningLocation.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.type()).isEqualTo(type);
            softly.assertThat(decoded.details()).isInstanceOf(detailsClass(type));
        });
    }

    @Test
    void deserialize_shouldKeepRawDetails_whenTypeUnknown() {
        final byte[] json = "{\"type\":\"gist_comment\",\"details\":{\"gist_id\":\"abc\",\"line\":3}}"
                .getBytes(StandardCharsets.UTF_8);

        final GitHubSecretScanningLocation decoded = CODEC.decode(json, GitHubSecretScanningLocation.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.type()).isEqualTo(GitHubSecretScanningLocationType.UNKNOWN);
            softly.assertThat(decoded.details())
                    .isInstanceOfSatisfying(GitHubSecretScanningUnknownLocationDetails.class, unknown -> {
                        assertThat(unknown.properties()).containsEntry("gist_id", "abc");
                        assertThat(unknown.properties()).containsEntry("line", 3);
                    });
        });
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenTypeUnknownAndDetailsMissing() {
        final byte[] json = "{\"type\":\"gist_comment\"}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubSecretScanningLocation.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenTypeMissing() {
        final byte[] json = "{\"details\":{}}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubSecretScanningLocation.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("type");
    }

    private static Class<? extends GitHubSecretScanningLocationDetails> detailsClass(
            final GitHubSecretScanningLocationType type) {
        return switch (type) {
            case COMMIT -> GitHubSecretScanningLocationCommit.class;
            case WIKI_COMMIT -> GitHubSecretScanningLocationWikiCommit.class;
            case ISSUE_TITLE -> GitHubSecretScanningLocationIssueTitle.class;
            case ISSUE_BODY -> GitHubSecretScanningLocationIssueBody.class;
            case ISSUE_COMMENT -> GitHubSecretScanningLocationIssueComment.class;
            case DISCUSSION_TITLE -> GitHubSecretScanningLocationDiscussionTitle.class;
            case DISCUSSION_BODY -> GitHubSecretScanningLocationDiscussionBody.class;
            case DISCUSSION_COMMENT -> GitHubSecretScanningLocationDiscussionComment.class;
            case PULL_REQUEST_TITLE -> GitHubSecretScanningLocationPullRequestTitle.class;
            case PULL_REQUEST_BODY -> GitHubSecretScanningLocationPullRequestBody.class;
            case PULL_REQUEST_COMMENT -> GitHubSecretScanningLocationPullRequestComment.class;
            case PULL_REQUEST_REVIEW -> GitHubSecretScanningLocationPullRequestReview.class;
            case PULL_REQUEST_REVIEW_COMMENT -> GitHubSecretScanningLocationPullRequestReviewComment.class;
            case UNKNOWN -> GitHubSecretScanningUnknownLocationDetails.class;
        };
    }
}
