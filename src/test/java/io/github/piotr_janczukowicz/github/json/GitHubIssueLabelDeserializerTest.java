package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueLabel;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubIssueLabelDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void deserialize_shouldSetNameOnly_whenJsonIsString() {
        final GitHubIssueLabel decoded = decode("\"bug\"");

        assertThat(decoded.name()).isEqualTo("bug");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[]", "5", "true"})
    void deserialize_shouldThrowDecodeException_whenRootIsNotALabel(final String json) {
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, GitHubIssueLabel.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void deserialize_shouldMapObjectFields_whenJsonIsObject() {
        final GitHubIssueLabel decoded = decode(
                "{\"id\":1,\"node_id\":\"n\",\"url\":\"https://example.com/labels/bug\",\"name\":\"bug\",\"description\":\"a bug\",\"color\":\"f29513\",\"default\":true}");

        assertSoftly(softly -> {
            softly.assertThat(decoded.id()).isEqualTo(1L);
            softly.assertThat(decoded.nodeId()).isEqualTo("n");
            softly.assertThat(decoded.url()).isEqualTo("https://example.com/labels/bug");
            softly.assertThat(decoded.name()).isEqualTo("bug");
            softly.assertThat(decoded.description()).isEqualTo("a bug");
            softly.assertThat(decoded.color()).isEqualTo("f29513");
            softly.assertThat(decoded.isDefault()).isTrue();
        });
    }

    @Test
    void deserialize_shouldMapArchivedBy_whenPresent() {
        final GitHubIssueLabel decoded = decode(
                "{\"name\":\"bug\",\"archived_by\":{\"login\":\"octocat\",\"id\":1,\"node_id\":\"n\",\"avatar_url\":\"a\",\"gravatar_id\":\"\",\"url\":\"u\",\"html_url\":\"h\",\"followers_url\":\"f\",\"following_url\":\"f\",\"gists_url\":\"g\",\"starred_url\":\"s\",\"subscriptions_url\":\"s\",\"organizations_url\":\"o\",\"repos_url\":\"r\",\"events_url\":\"e\",\"received_events_url\":\"r\",\"type\":\"User\",\"site_admin\":false}}");

        assertThat(decoded.archivedBy()).isNotNull();
        assertThat(decoded.archivedBy().login()).isEqualTo("octocat");
    }

    @Test
    void deserialize_shouldLeaveOptionalFieldsNull_whenObjectOmitsThem() {
        final GitHubIssueLabel decoded = decode("{}");

        assertSoftly(softly -> {
            softly.assertThat(decoded.id()).isNull();
            softly.assertThat(decoded.name()).isNull();
            softly.assertThat(decoded.isDefault()).isNull();
        });
    }

    @Test
    void deserialize_shouldMapDefaultFalse_whenPresent() {
        final GitHubIssueLabel decoded = decode("{\"default\":false}");

        assertThat(decoded.isDefault()).isFalse();
    }

    @Test
    void deserialize_shouldIgnoreUnknownField_whenObjectContainsIt() {
        final GitHubIssueLabel decoded = decode("{\"name\":\"bug\",\"extra\":{\"nested\":true}}");

        assertThat(decoded.name()).isEqualTo("bug");
    }

    @Test
    void deserialize_shouldTreatExplicitNullFieldsAsAbsent_whenJsonHasNull() {
        final GitHubIssueLabel decoded = decode("{\"node_id\":null,\"url\":null,\"name\":null,\"default\":null}");

        assertSoftly(softly -> {
            softly.assertThat(decoded.nodeId()).isNull();
            softly.assertThat(decoded.url()).isNull();
            softly.assertThat(decoded.name()).isNull();
            softly.assertThat(decoded.isDefault()).isNull();
        });
    }

    private static GitHubIssueLabel decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), GitHubIssueLabel.class);
    }
}
