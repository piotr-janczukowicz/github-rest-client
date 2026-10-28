package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.issues.IssuesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemContent;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemContentType;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemSimple;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemSimpleBuilder;
import io.github.piotr_janczukowicz.github.api.projects.ProjectsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

@UnitTest
class GitHubProjectItemSimpleDeserializerTest {

    private static final JsonMapper TREE = JsonMapper.builder().build();

    @Test
    void deserialize_shouldDecodeFullItem_whenFixtureHasOptionalFields() {
        final byte[] json = FileUtils.readBytes(GitHubProjectItemSimple.class, "item-simple.json");

        final GitHubProjectItemSimple item = GitHubJsonCodecs.defaults().decode(json, GitHubProjectItemSimple.class);

        assertThat(item).isEqualTo(ProjectsCatalogFixtures.itemSimpleDraft());
    }

    @Test
    void deserialize_shouldLeaveOptionalsNull_whenFieldsAbsent() {
        final ObjectNode envelope = TREE.createObjectNode();
        envelope.put("id", 3L);
        envelope.put("content_type", GitHubProjectItemContentType.ISSUE.wireValue());
        envelope.put("created_at", "2022-04-28T12:00:00Z");
        envelope.put("updated_at", "2022-04-28T12:00:00Z");

        final GitHubProjectItemSimple item =
                GitHubJsonCodecs.defaults().decode(TREE.writeValueAsBytes(envelope), GitHubProjectItemSimple.class);

        assertSoftly(softly -> {
            softly.assertThat(item.content()).isNull();
            softly.assertThat(item.creator()).isNull();
            softly.assertThat(item.nodeId()).isNull();
            softly.assertThat(item.archivedAt()).isNull();
            softly.assertThat(item.projectUrl()).isNull();
            softly.assertThat(item.itemUrl()).isNull();
        });
    }

    @Test
    void deserialize_shouldLeaveOptionalsNull_whenFieldsAreJsonNull() {
        final ObjectNode envelope = TREE.createObjectNode();
        envelope.put("id", 3L);
        envelope.putNull("node_id");
        envelope.put("content_type", GitHubProjectItemContentType.ISSUE.wireValue());
        envelope.put("created_at", "2022-04-28T12:00:00Z");
        envelope.put("updated_at", "2022-04-28T12:00:00Z");
        envelope.putNull("archived_at");
        envelope.putNull("project_url");
        envelope.putNull("item_url");

        final GitHubProjectItemSimple item =
                GitHubJsonCodecs.defaults().decode(TREE.writeValueAsBytes(envelope), GitHubProjectItemSimple.class);

        assertSoftly(softly -> {
            softly.assertThat(item.nodeId()).isNull();
            softly.assertThat(item.archivedAt()).isEqualTo(OptionalNull.ofNull());
            softly.assertThat(item.projectUrl()).isNull();
            softly.assertThat(item.itemUrl()).isNull();
        });
    }

    @Test
    void encode_shouldOmitArchivedAt_whenWrapperAbsent() {
        final GitHubProjectItemSimple item = GitHubProjectItemSimpleBuilder.builder(
                        ProjectsCatalogFixtures.itemSimpleDraft())
                .archivedAt(null)
                .build();

        final JsonNode encoded = TREE.readTree(GitHubJsonCodecs.defaults().encode(item));

        assertThat(encoded.path("archived_at").isMissingNode()).isTrue();
    }

    @Test
    void encode_shouldWriteJsonNull_whenArchivedAtIsOptionalNull() {
        final GitHubProjectItemSimple item = GitHubProjectItemSimpleBuilder.builder(
                        ProjectsCatalogFixtures.itemSimpleDraft())
                .archivedAt(OptionalNull.ofNull())
                .build();

        final JsonNode encoded = TREE.readTree(GitHubJsonCodecs.defaults().encode(item));

        assertThat(encoded.get("archived_at").isNull()).isTrue();
    }

    @Test
    void deserialize_shouldDecodeIssueContent_whenContentTypeIsIssue() {
        final ObjectNode envelope = minimalItem();
        envelope.set("content", TREE.readTree(FileUtils.readBytes(GitHubIssue.class, "issue.json")));

        final GitHubProjectItemSimple item =
                GitHubJsonCodecs.defaults().decode(TREE.writeValueAsBytes(envelope), GitHubProjectItemSimple.class);

        assertThat(item.content()).isEqualTo(new GitHubProjectItemContent.Issue(IssuesCatalogFixtures.issue()));
    }

    @Test
    void deserialize_shouldDecodePullRequestContent_whenContentTypeIsPullRequest() {
        final ObjectNode envelope = minimalItem();
        envelope.put("content_type", GitHubProjectItemContentType.PULL_REQUEST.wireValue());
        envelope.set(
                "content",
                TREE.readTree(FileUtils.readBytes(GitHubPullRequestSimple.class, "pull-request-simple.json")));

        final GitHubProjectItemSimple item =
                GitHubJsonCodecs.defaults().decode(TREE.writeValueAsBytes(envelope), GitHubProjectItemSimple.class);

        assertThat(item.content())
                .isEqualTo(new GitHubProjectItemContent.PullRequest(RepositoriesCatalogFixtures.pullRequestSimple()));
    }

    @Test
    void deserialize_shouldReturnUnknownContent_whenContentTypeUnknown() {
        final ObjectNode envelope = minimalItem();
        envelope.put("content_type", "hologram");
        envelope.putObject("content").put("id", 1);
        final byte[] json = TREE.writeValueAsBytes(envelope);

        final GitHubProjectItemSimple item = GitHubJsonCodecs.defaults().decode(json, GitHubProjectItemSimple.class);

        assertSoftly(softly -> {
            softly.assertThat(item.contentType()).isEqualTo(GitHubProjectItemContentType.UNKNOWN);
            softly.assertThat(item.content()).isEqualTo(new GitHubProjectItemContent.Unknown(Map.of("id", 1)));
        });
    }

    @Test
    void deserialize_shouldLeaveContentNull_whenContentTypeUnknownAndContentAbsent() {
        final ObjectNode envelope = minimalItem();
        envelope.put("content_type", "hologram");
        final byte[] json = TREE.writeValueAsBytes(envelope);

        final GitHubProjectItemSimple item = GitHubJsonCodecs.defaults().decode(json, GitHubProjectItemSimple.class);

        assertSoftly(softly -> {
            softly.assertThat(item.contentType()).isEqualTo(GitHubProjectItemContentType.UNKNOWN);
            softly.assertThat(item.content()).isNull();
        });
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenContentTypeMissing() {
        final ObjectNode envelope = minimalItem();
        envelope.remove("content_type");
        final byte[] json = TREE.writeValueAsBytes(envelope);

        final JsonCodec codec = GitHubJsonCodecs.defaults();

        final Throwable thrown = catchThrowable(() -> codec.decode(json, GitHubProjectItemSimple.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("content_type");
    }

    @Test
    void decode_shouldKeepEveryItem_whenPageHoldsItemWithUnknownContentType() {
        final ObjectNode known = minimalItem();
        final ObjectNode unknown = minimalItem();
        unknown.put("content_type", "hologram");
        final byte[] page =
                TREE.writeValueAsBytes(TREE.createArrayNode().add(known).add(unknown));

        final List<GitHubProjectItemSimple> items =
                GitHubJsonCodecs.defaults().decode(page, JsonTypes.listOf(GitHubProjectItemSimple.class));

        assertThat(items)
                .extracting(GitHubProjectItemSimple::contentType)
                .containsExactly(GitHubProjectItemContentType.ISSUE, GitHubProjectItemContentType.UNKNOWN);
    }

    @ParameterizedTest
    @ValueSource(strings = {"created_at", "updated_at"})
    void deserialize_shouldThrowDecodeException_whenTimestampAbsent(final String timestamp) {
        final ObjectNode envelope = minimalItem();
        envelope.remove(timestamp);
        final byte[] json = TREE.writeValueAsBytes(envelope);

        final JsonCodec codec = GitHubJsonCodecs.defaults();

        final Throwable thrown = catchThrowable(() -> codec.decode(json, GitHubProjectItemSimple.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenIdAbsent() {
        final ObjectNode envelope = minimalItem();
        envelope.remove("id");
        final byte[] json = TREE.writeValueAsBytes(envelope);

        final JsonCodec codec = GitHubJsonCodecs.defaults();

        final Throwable thrown = catchThrowable(() -> codec.decode(json, GitHubProjectItemSimple.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).cause().hasMessageContaining("project item id");
    }

    private static ObjectNode minimalItem() {
        final ObjectNode envelope = TREE.createObjectNode();
        envelope.put("id", 3L);
        envelope.put("content_type", GitHubProjectItemContentType.ISSUE.wireValue());
        envelope.put("created_at", "2022-04-28T12:00:00Z");
        envelope.put("updated_at", "2022-04-28T12:00:00Z");
        return envelope;
    }
}
