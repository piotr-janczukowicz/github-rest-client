package io.github.piotr_janczukowicz.github.json;

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
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.jsontype.TypeDeserializer;

/**
 * Decodes {@code secret-scanning-first-detected-location} by the properties that tell the shapes apart.
 *
 * <p>Jackson deduction cannot be used for the whole union: the {@code actions_log} shape (five line and column
 * properties) is a subset of the commit and wiki commit shapes. The first discriminating property the object has
 * selects the record; an object with all of the {@code actions_log} properties and none of those is the Actions log
 * location; any other object is kept raw as a {@link GitHubSecretScanningUnknownLocationDetails}.
 */
final class GitHubSecretScanningFirstDetectedLocationDeserializer
        extends StdDeserializer<GitHubSecretScanningFirstDetectedLocation> {

    private static final Map<String, Class<? extends GitHubSecretScanningFirstDetectedLocation>> DISCRIMINATORS =
            discriminators();

    private static final List<String> ACTIONS_LOG_PROPERTIES =
            List.of("path", "start_line", "end_line", "start_column", "end_column");

    GitHubSecretScanningFirstDetectedLocationDeserializer() {
        super(GitHubSecretScanningFirstDetectedLocation.class);
    }

    @Override
    public GitHubSecretScanningFirstDetectedLocation deserialize(
            final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            final Class<? extends GitHubSecretScanningFirstDetectedLocation> type = recordType(json);
            return type == null ? unknown(json) : json.read(type);
        }
    }

    // The union carries @JsonTypeInfo for its members; this deserializer decodes the union itself.
    @Override
    public Object deserializeWithType(
            final JsonParser parser, final DeserializationContext context, final TypeDeserializer typeDeserializer) {
        return deserialize(parser, context);
    }

    private static @Nullable Class<? extends GitHubSecretScanningFirstDetectedLocation> recordType(
            final BufferedJson json) {
        for (final Map.Entry<String, Class<? extends GitHubSecretScanningFirstDetectedLocation>> entry :
                DISCRIMINATORS.entrySet()) {
            if (json.hasField(entry.getKey())) {
                return entry.getValue();
            }
        }
        return ACTIONS_LOG_PROPERTIES.stream().allMatch(json::hasField)
                ? GitHubSecretScanningLocationActionsLog.class
                : null;
    }

    @SuppressWarnings("unchecked")
    private static GitHubSecretScanningUnknownLocationDetails unknown(final BufferedJson json) {
        final Map<String, Object> properties = new LinkedHashMap<>();
        json.read(Map.class).forEach((name, value) -> properties.put(String.valueOf(name), value));
        return new GitHubSecretScanningUnknownLocationDetails(properties);
    }

    private static Map<String, Class<? extends GitHubSecretScanningFirstDetectedLocation>> discriminators() {
        final Map<String, Class<? extends GitHubSecretScanningFirstDetectedLocation>> found = new LinkedHashMap<>();
        found.put("blob_url", GitHubSecretScanningLocationCommit.class);
        found.put("page_url", GitHubSecretScanningLocationWikiCommit.class);
        found.put("issue_title_url", GitHubSecretScanningLocationIssueTitle.class);
        found.put("issue_body_url", GitHubSecretScanningLocationIssueBody.class);
        found.put("issue_comment_url", GitHubSecretScanningLocationIssueComment.class);
        found.put("discussion_title_url", GitHubSecretScanningLocationDiscussionTitle.class);
        found.put("discussion_body_url", GitHubSecretScanningLocationDiscussionBody.class);
        found.put("discussion_comment_url", GitHubSecretScanningLocationDiscussionComment.class);
        found.put("pull_request_title_url", GitHubSecretScanningLocationPullRequestTitle.class);
        found.put("pull_request_body_url", GitHubSecretScanningLocationPullRequestBody.class);
        found.put("pull_request_comment_url", GitHubSecretScanningLocationPullRequestComment.class);
        found.put("pull_request_review_url", GitHubSecretScanningLocationPullRequestReview.class);
        found.put("pull_request_review_comment_url", GitHubSecretScanningLocationPullRequestReviewComment.class);
        return found;
    }
}
