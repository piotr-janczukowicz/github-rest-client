package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.activity.GitHubActor;
import io.github.piotr_janczukowicz.github.api.activity.GitHubCommitCommentEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubCreateEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubDeleteEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubDiscussionEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubEventPayload;
import io.github.piotr_janczukowicz.github.api.activity.GitHubEventRepo;
import io.github.piotr_janczukowicz.github.api.activity.GitHubForkEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubGollumEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubIssueCommentEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubIssuesEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubMemberEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubPublicEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubPullRequestEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubPullRequestReviewCommentEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubPullRequestReviewEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubPushEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubReleaseEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubUnknownEventPayload;
import io.github.piotr_janczukowicz.github.api.activity.GitHubWatchEvent;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes an event; the {@code payload} record is chosen by the sibling {@code type} property.
 *
 * <p>An event type that this library does not know does not fail the decode: its payload is kept as a
 * {@link GitHubUnknownEventPayload}, and {@link GitHubEvent#type()} still names the type. The spec allows a
 * {@code null} {@code type}; such an event keeps its payload the same way, with a {@code null} type. A missing
 * {@code type} property does fail the decode. The type can get its own record here when GitHub's documentation or the
 * spec lists it.
 */
final class GitHubEventDeserializer extends StdDeserializer<GitHubEvent> {

    private static final String TYPE = "type";
    private static final String PAYLOAD = "payload";

    GitHubEventDeserializer() {
        super(GitHubEvent.class);
    }

    @Override
    public GitHubEvent deserialize(final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            if (!json.hasField(TYPE)) {
                throw json.mismatch("Missing GitHub event type");
            }
            final String type = json.text(TYPE);
            final Class<? extends GitHubEventPayload> payloadType = type == null ? null : payloadType(type);
            final GitHubEventPayload payload =
                    payloadType == null ? unknownPayload(json) : json.readRequiredField(PAYLOAD, payloadType);

            return new GitHubEvent(
                    json.requiredText("id"),
                    type,
                    json.readRequiredField("actor", GitHubActor.class),
                    json.readRequiredField("repo", GitHubEventRepo.class),
                    json.readField("org", GitHubActor.class),
                    payload,
                    json.readRequiredField("public", Boolean.class),
                    json.readField("created_at", Instant.class));
        }
    }

    @SuppressWarnings("unchecked")
    private static GitHubUnknownEventPayload unknownPayload(final BufferedJson json) {
        final Map<String, Object> properties = new LinkedHashMap<>();
        json.readRequiredField(PAYLOAD, Map.class)
                .forEach((name, value) -> properties.put(String.valueOf(name), value));
        return new GitHubUnknownEventPayload(properties);
    }

    private static @Nullable Class<? extends GitHubEventPayload> payloadType(final String type) {
        return switch (type) {
            case "CreateEvent" -> GitHubCreateEvent.class;
            case "DeleteEvent" -> GitHubDeleteEvent.class;
            case "DiscussionEvent" -> GitHubDiscussionEvent.class;
            case "IssuesEvent" -> GitHubIssuesEvent.class;
            case "IssueCommentEvent" -> GitHubIssueCommentEvent.class;
            case "ForkEvent" -> GitHubForkEvent.class;
            case "GollumEvent" -> GitHubGollumEvent.class;
            case "MemberEvent" -> GitHubMemberEvent.class;
            case "PublicEvent" -> GitHubPublicEvent.class;
            case "PushEvent" -> GitHubPushEvent.class;
            case "PullRequestEvent" -> GitHubPullRequestEvent.class;
            case "PullRequestReviewCommentEvent" -> GitHubPullRequestReviewCommentEvent.class;
            case "PullRequestReviewEvent" -> GitHubPullRequestReviewEvent.class;
            case "ReleaseEvent" -> GitHubReleaseEvent.class;
            case "WatchEvent" -> GitHubWatchEvent.class;
            case "CommitCommentEvent" -> GitHubCommitCommentEvent.class;
            default -> null;
        };
    }
}
