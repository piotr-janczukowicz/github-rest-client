package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotEnterpriseContentExclusionRule;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubCopilotEnterpriseContentExclusionRuleDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReadPath_whenJsonString() {
        final GitHubCopilotEnterpriseContentExclusionRule rule = CODEC.decode(
                "\"/src/kernel.rs\"".getBytes(StandardCharsets.UTF_8),
                GitHubCopilotEnterpriseContentExclusionRule.class);

        assertThat(rule.path()).isEqualTo("/src/kernel.rs");
    }

    @Test
    void decode_shouldReadIfAnyMatch_whenObject() {
        final GitHubCopilotEnterpriseContentExclusionRule rule = CODEC.decode(
                "{\"ifAnyMatch\":[\"*.rs\"]}".getBytes(StandardCharsets.UTF_8),
                GitHubCopilotEnterpriseContentExclusionRule.class);

        assertThat(rule.ifAnyMatch().ifAnyMatch()).containsExactly("*.rs");
    }

    @Test
    void decode_shouldReadIfNoneMatch_whenObject() {
        final GitHubCopilotEnterpriseContentExclusionRule rule = CODEC.decode(
                "{\"ifNoneMatch\":[\"*.md\"]}".getBytes(StandardCharsets.UTF_8),
                GitHubCopilotEnterpriseContentExclusionRule.class);

        assertThat(rule.ifNoneMatch().ifNoneMatch()).containsExactly("*.md");
    }

    @Test
    void decode_shouldThrow_whenObjectHasNoKnownKey() {
        final Throwable thrown = catchThrowable(() -> CODEC.decode(
                "{\"other\":[]}".getBytes(StandardCharsets.UTF_8), GitHubCopilotEnterpriseContentExclusionRule.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).hasStackTraceContaining("ifAnyMatch or ifNoneMatch");
    }

    @Test
    void decode_shouldThrow_whenObjectHasBothIfAnyMatchAndIfNoneMatch() {
        final byte[] json = "{\"ifAnyMatch\":[\"a\"],\"ifNoneMatch\":[\"b\"]}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown =
                catchThrowable(() -> CODEC.decode(json, GitHubCopilotEnterpriseContentExclusionRule.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasStackTraceContaining("only one of ifAnyMatch or ifNoneMatch");
    }

    @Test
    void decode_shouldThrow_whenIfAnyMatchNotArray() {
        final Throwable thrown = catchThrowable(() -> CODEC.decode(
                "{\"ifAnyMatch\":\"*.rs\"}".getBytes(StandardCharsets.UTF_8),
                GitHubCopilotEnterpriseContentExclusionRule.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasStackTraceContaining("pattern list must be a string array");
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"ifAnyMatch\":[{}]}", "{\"ifAnyMatch\":[null]}", "{\"ifNoneMatch\":[5]}"})
    void decode_shouldThrow_whenPatternListHoldsAnythingButStrings(final String json) {
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown =
                catchThrowable(() -> CODEC.decode(bytes, GitHubCopilotEnterpriseContentExclusionRule.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasStackTraceContaining("pattern list must hold strings only");
    }

    @Test
    void decode_shouldThrow_whenIfNoneMatchNotArray() {
        final Throwable thrown = catchThrowable(() -> CODEC.decode(
                "{\"ifNoneMatch\":{}}".getBytes(StandardCharsets.UTF_8),
                GitHubCopilotEnterpriseContentExclusionRule.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasStackTraceContaining("pattern list must be a string array");
    }

    @Test
    void decode_shouldThrow_whenArrayNotObjectOrString() {
        final Throwable thrown = catchThrowable(() -> CODEC.decode(
                "[\"*.rs\"]".getBytes(StandardCharsets.UTF_8), GitHubCopilotEnterpriseContentExclusionRule.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasStackTraceContaining("value must be a string, or an object with ifAnyMatch or ifNoneMatch");
    }

    @Test
    void decode_shouldThrow_whenNumber() {
        final Throwable thrown = catchThrowable(() ->
                CODEC.decode("42".getBytes(StandardCharsets.UTF_8), GitHubCopilotEnterpriseContentExclusionRule.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasStackTraceContaining("value must be a string, or an object with ifAnyMatch or ifNoneMatch");
    }

    @Test
    void decode_shouldReadEmptyList_whenIfAnyMatchEmpty() {
        final GitHubCopilotEnterpriseContentExclusionRule rule = CODEC.decode(
                "{\"ifAnyMatch\":[]}".getBytes(StandardCharsets.UTF_8),
                GitHubCopilotEnterpriseContentExclusionRule.class);

        assertThat(rule.ifAnyMatch().ifAnyMatch()).isEmpty();
    }
}
