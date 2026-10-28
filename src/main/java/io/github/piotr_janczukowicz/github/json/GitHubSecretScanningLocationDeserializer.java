package io.github.piotr_janczukowicz.github.json;

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
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes {@code secret-scanning-location} using the sibling {@code type} property as discriminator.
 *
 * <p>A location {@code type} that this library does not know does not fail the decode: the location gets the type
 * {@link GitHubSecretScanningLocationType#UNKNOWN} and its {@code details} are kept as a
 * {@link GitHubSecretScanningUnknownLocationDetails}. A missing {@code type} or {@code details} property does fail the
 * decode.
 */
final class GitHubSecretScanningLocationDeserializer extends StdDeserializer<GitHubSecretScanningLocation> {

    private static final String TYPE = "type";
    private static final String DETAILS = "details";

    GitHubSecretScanningLocationDeserializer() {
        super(GitHubSecretScanningLocation.class);
    }

    @Override
    public GitHubSecretScanningLocation deserialize(final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            final GitHubSecretScanningLocationType type =
                    json.requiredWire(TYPE, GitHubSecretScanningLocationType::fromWire);
            final Class<? extends GitHubSecretScanningLocationDetails> detailsType = detailsType(type);
            final GitHubSecretScanningLocationDetails details =
                    detailsType == null ? unknownDetails(json) : json.readRequiredField(DETAILS, detailsType);
            return new GitHubSecretScanningLocation(type, details);
        }
    }

    @SuppressWarnings("unchecked")
    private static GitHubSecretScanningUnknownLocationDetails unknownDetails(final BufferedJson json) {
        final Map<String, Object> properties = new LinkedHashMap<>();
        json.readRequiredField(DETAILS, Map.class)
                .forEach((name, value) -> properties.put(String.valueOf(name), value));
        return new GitHubSecretScanningUnknownLocationDetails(properties);
    }

    private static @Nullable Class<? extends GitHubSecretScanningLocationDetails> detailsType(
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
            case UNKNOWN -> null;
        };
    }
}
