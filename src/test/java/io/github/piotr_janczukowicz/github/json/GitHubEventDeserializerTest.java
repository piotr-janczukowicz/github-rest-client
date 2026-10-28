package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.activity.GitHubCommitCommentEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubCreateEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubDeleteEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubDiscussionEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubEvent;
import io.github.piotr_janczukowicz.github.api.activity.GitHubEventPayload;
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
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubEventDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();
    private static final String ACTOR = "{\"id\":1,\"login\":\"octocat\",\"gravatar_id\":null,"
            + "\"url\":\"https://api.github.com/users/octocat\","
            + "\"avatar_url\":\"https://github.com/images/error/octocat_happy.gif\"}";
    private static final String REPO = "{\"id\":1,\"name\":\"octocat/Hello-World\","
            + "\"url\":\"https://api.github.com/repos/octocat/Hello-World\"}";
    private static final String ORG = "{\"id\":2,\"login\":\"octo-org\",\"gravatar_id\":null,"
            + "\"url\":\"https://api.github.com/users/octo-org\","
            + "\"avatar_url\":\"https://github.com/images/error/octo-org_happy.gif\"}";
    private static final String PUSH_PAYLOAD =
            "{\"repository_id\":1,\"push_id\":2,\"ref\":\"refs/heads/main\",\"head\":\"abc\",\"before\":\"def\"}";
    private static final String CREATED_AT = "\"created_at\":\"2024-01-01T00:00:00Z\"";

    @ParameterizedTest
    @MethodSource("typedPayloads")
    void decode_shouldReturnTypedPayload_whenKnownType(
            final String type, final String fileName, final Class<? extends GitHubEventPayload> payloadType) {
        final byte[] json = event(type, FileUtils.readString(GitHubEvent.class, fileName), "", CREATED_AT);

        final GitHubEvent decoded = CODEC.decode(json, GitHubEvent.class);

        assertThat(decoded.payload()).isInstanceOf(payloadType);
    }

    @Test
    void decode_shouldReturnTypedPayload_whenTypeIsPushEvent() {
        final byte[] json = event("PushEvent", PUSH_PAYLOAD, "", CREATED_AT);

        final GitHubEvent decoded = CODEC.decode(json, GitHubEvent.class);

        assertThat(decoded.payload()).isInstanceOf(GitHubPushEvent.class);
    }

    @Test
    void decode_shouldReturnEmptyPayload_whenTypeIsPublicEvent() {
        final byte[] json = event("PublicEvent", "{}", "", CREATED_AT);

        final GitHubEvent decoded = CODEC.decode(json, GitHubEvent.class);

        assertThat(decoded.payload()).isInstanceOf(GitHubPublicEvent.class);
    }

    @Test
    void decode_shouldMapOrgAndCreatedAt_whenPresent() {
        final byte[] json = event("PushEvent", PUSH_PAYLOAD, ",\"org\":" + ORG, CREATED_AT);

        final GitHubEvent decoded = CODEC.decode(json, GitHubEvent.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.org()).isNotNull();
            softly.assertThat(decoded.org().login()).isEqualTo("octo-org");
            softly.assertThat(decoded.createdAt()).isEqualTo(Instant.parse("2024-01-01T00:00:00Z"));
        });
    }

    @Test
    void decode_shouldLeaveOrgAndCreatedAtNull_whenAbsent() {
        final byte[] json = event("PushEvent", PUSH_PAYLOAD, "", "");

        final GitHubEvent decoded = CODEC.decode(json, GitHubEvent.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.org()).isNull();
            softly.assertThat(decoded.createdAt()).isNull();
        });
    }

    @Test
    void decode_shouldLeaveOrgAndCreatedAtNull_whenExplicitlyNull() {
        final byte[] json = event("PushEvent", PUSH_PAYLOAD, ",\"org\":null", "\"created_at\":null");

        final GitHubEvent decoded = CODEC.decode(json, GitHubEvent.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.org()).isNull();
            softly.assertThat(decoded.createdAt()).isNull();
        });
    }

    @Test
    void decode_shouldThrow_whenTypeMissing() {
        final byte[] json = eventWithoutType();

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubEvent.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("Missing GitHub event type");
    }

    @ParameterizedTest
    @ValueSource(strings = {"PushEvent", "SponsorshipEvent"})
    void decode_shouldThrow_whenPayloadMissing(final String type) {
        final byte[] json = eventWithoutPayload(type);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubEvent.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"PushEvent", "SponsorshipEvent"})
    void decode_shouldThrow_whenPayloadNull(final String type) {
        final byte[] json = event(type, "null", "", CREATED_AT);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubEvent.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @ParameterizedTest
    @MethodSource("malformedPayloads")
    void decode_shouldThrow_whenPayloadIsMalformed(final String type, final String payload) {
        final byte[] json = event(type, payload, "", CREATED_AT);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubEvent.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void decode_shouldThrow_whenTypeNullAndPayloadMissing() {
        final byte[] json = eventWithNullTypeAndWithoutPayload();

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubEvent.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void decode_shouldKeepRawPayload_whenTypeUnknown() {
        final byte[] json = event(
                "SponsorshipEvent",
                "{\"action\":\"created\",\"sponsorship\":{\"tier\":{\"name\":\"gold\"}}}",
                "",
                CREATED_AT);

        final GitHubEvent decoded = CODEC.decode(json, GitHubEvent.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.type()).isEqualTo("SponsorshipEvent");
            softly.assertThat(decoded.payload()).isInstanceOfSatisfying(GitHubUnknownEventPayload.class, unknown -> {
                assertThat(unknown.properties()).containsEntry("action", "created");
                assertThat(unknown.properties()).containsKey("sponsorship");
            });
        });
    }

    @Test
    void decode_shouldKeepRawPayload_whenTypeNull() {
        final byte[] json = eventWithNullType();

        final GitHubEvent decoded = CODEC.decode(json, GitHubEvent.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.type()).isNull();
            softly.assertThat(decoded.payload())
                    .isInstanceOfSatisfying(
                            GitHubUnknownEventPayload.class,
                            unknown -> assertThat(unknown.properties()).containsKey("ref"));
        });
    }

    @Test
    void decode_shouldKeepEveryEventOfThePage_whenOneTypeIsUnknown() {
        final String known = new String(event("PushEvent", PUSH_PAYLOAD, "", CREATED_AT), StandardCharsets.UTF_8);
        final String unknown = new String(event("SponsorshipEvent", "{}", "", CREATED_AT), StandardCharsets.UTF_8);
        final byte[] page = ("[" + known + "," + unknown + "]").getBytes(StandardCharsets.UTF_8);

        final List<GitHubEvent> decoded = CODEC.decode(page, JsonTypes.listOf(GitHubEvent.class));

        assertThat(decoded).extracting(GitHubEvent::type).containsExactly("PushEvent", "SponsorshipEvent");
    }

    @Test
    void encode_shouldWriteThePayloadAsItWasRead_whenTypeUnknown() {
        final GitHubUnknownEventPayload payload = new GitHubUnknownEventPayload(Map.of("action", "created"));

        final String json = new String(CODEC.encode(payload), StandardCharsets.UTF_8);

        assertThat(json).isEqualTo("{\"action\":\"created\"}");
    }

    @Test
    void encode_shouldWriteEmptyObject_whenPayloadIsPublicEvent() {
        final String json = new String(CODEC.encode(new GitHubPublicEvent()), StandardCharsets.UTF_8);

        assertThat(json).isEqualTo("{}");
    }

    @Test
    void encode_shouldWriteWholeEvent_whenPayloadIsPublicEvent() {
        final GitHubEvent decoded = CODEC.decode(event("PublicEvent", "{}", "", CREATED_AT), GitHubEvent.class);

        final String json = new String(CODEC.encode(decoded), StandardCharsets.UTF_8);

        assertThat(json).contains("\"type\":\"PublicEvent\"").contains("\"payload\":{}");
    }

    private static Stream<Arguments> typedPayloads() {
        return Stream.of(
                Arguments.of("CreateEvent", "create-event.json", GitHubCreateEvent.class),
                Arguments.of("DeleteEvent", "delete-event.json", GitHubDeleteEvent.class),
                Arguments.of("DiscussionEvent", "discussion-event.json", GitHubDiscussionEvent.class),
                Arguments.of("IssuesEvent", "issues-event.json", GitHubIssuesEvent.class),
                Arguments.of("IssueCommentEvent", "issue-comment-event.json", GitHubIssueCommentEvent.class),
                Arguments.of("ForkEvent", "fork-event.json", GitHubForkEvent.class),
                Arguments.of("GollumEvent", "gollum-event.json", GitHubGollumEvent.class),
                Arguments.of("MemberEvent", "member-event.json", GitHubMemberEvent.class),
                Arguments.of("PullRequestEvent", "pull-request-event.json", GitHubPullRequestEvent.class),
                Arguments.of(
                        "PullRequestReviewCommentEvent",
                        "pull-request-review-comment-event.json",
                        GitHubPullRequestReviewCommentEvent.class),
                Arguments.of(
                        "PullRequestReviewEvent", "pull-request-review-event.json", GitHubPullRequestReviewEvent.class),
                Arguments.of("ReleaseEvent", "release-event.json", GitHubReleaseEvent.class),
                Arguments.of("WatchEvent", "watch-event.json", GitHubWatchEvent.class),
                Arguments.of("CommitCommentEvent", "commit-comment-event.json", GitHubCommitCommentEvent.class));
    }

    private static Stream<Arguments> malformedPayloads() {
        return Stream.of(
                Arguments.of("PushEvent", "[]"),
                Arguments.of("PushEvent", "\"x\""),
                Arguments.of("PushEvent", "5"),
                Arguments.of("PushEvent", "{\"push_id\":\"not-a-number\"}"),
                Arguments.of("SponsorshipEvent", "[]"),
                Arguments.of("SponsorshipEvent", "\"x\""));
    }

    private static byte[] event(
            final String type, final String payload, final String orgFragment, final String createdAtFragment) {
        final String createdAt = createdAtFragment.isEmpty() ? "" : "," + createdAtFragment;
        final String json = "{\"id\":\"1\",\"type\":\"" + type + "\",\"actor\":" + ACTOR + ",\"repo\":" + REPO
                + orgFragment + ",\"payload\":" + payload + ",\"public\":true" + createdAt + "}";
        return json.getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] eventWithNullType() {
        final String json = "{\"id\":\"1\",\"type\":null,\"actor\":" + ACTOR + ",\"repo\":" + REPO + ",\"payload\":"
                + PUSH_PAYLOAD + ",\"public\":true," + CREATED_AT + "}";
        return json.getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] eventWithoutPayload(final String type) {
        final String json = "{\"id\":\"1\",\"type\":\"" + type + "\",\"actor\":" + ACTOR + ",\"repo\":" + REPO
                + ",\"public\":true," + CREATED_AT + "}";
        return json.getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] eventWithNullTypeAndWithoutPayload() {
        final String json = "{\"id\":\"1\",\"type\":null,\"actor\":" + ACTOR + ",\"repo\":" + REPO + ",\"public\":true,"
                + CREATED_AT + "}";
        return json.getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] eventWithoutType() {
        final String json = "{\"id\":\"1\",\"actor\":" + ACTOR + ",\"repo\":" + REPO + ",\"payload\":" + PUSH_PAYLOAD
                + ",\"public\":true," + CREATED_AT + "}";
        return json.getBytes(StandardCharsets.UTF_8);
    }
}
