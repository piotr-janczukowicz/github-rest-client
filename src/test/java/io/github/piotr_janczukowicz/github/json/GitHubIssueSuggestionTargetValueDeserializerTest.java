package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueSuggestion;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueSuggestionTargetValue;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubIssueSuggestionTargetValueDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnNullTargetValue_whenFieldIsJsonNull() {
        final byte[] json = FileUtils.readBytes(
                GitHubIssueSuggestionTargetValueDeserializerTest.class, "issue-suggestion-null-target-value.json");

        final GitHubIssueSuggestion decoded = CODEC.decode(json, GitHubIssueSuggestion.class);

        assertThat(decoded.targetValue()).isNull();
    }

    @Test
    void decode_shouldReturnText_whenJsonIsString() {
        final GitHubIssueSuggestionTargetValue decoded = decode("issue-suggestion-target-value-text.json");

        assertThat(decoded.textValue()).isEqualTo("bug");
    }

    @Test
    void decode_shouldReturnNumber_whenJsonIsInteger() {
        final GitHubIssueSuggestionTargetValue decoded = decode("issue-suggestion-target-value-integer.json");

        assertThat(decoded.numberValue()).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void decode_shouldReturnNumber_whenJsonIsFloat() {
        final GitHubIssueSuggestionTargetValue decoded = decode("issue-suggestion-target-value-number.json");

        assertThat(decoded.numberValue()).isEqualTo(new BigDecimal("1.5"));
    }

    @Test
    void decode_shouldReturnFlag_whenJsonIsTrue() {
        final GitHubIssueSuggestionTargetValue decoded = decode("issue-suggestion-target-value-flag.json");

        assertThat(decoded.booleanValue()).isTrue();
    }

    @Test
    void decode_shouldReturnFlag_whenJsonIsFalse() {
        final GitHubIssueSuggestionTargetValue decoded = decode("issue-suggestion-target-value-false.json");

        assertThat(decoded.booleanValue()).isFalse();
    }

    @Test
    void decode_shouldReturnTexts_whenJsonIsArray() {
        final GitHubIssueSuggestionTargetValue decoded = decode("issue-suggestion-target-value-list.json");

        assertThat(decoded.listValue()).containsExactly("a");
    }

    @Test
    void decode_shouldThrow_whenJsonIsObject() {
        final byte[] json = FileUtils.readBytes(
                GitHubIssueSuggestionTargetValueDeserializerTest.class, "issue-suggestion-target-value-object.json");

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubIssueSuggestionTargetValue.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("target_value");
    }

    private static GitHubIssueSuggestionTargetValue decode(final String fileName) {
        return CODEC.decode(
                FileUtils.readBytes(GitHubIssueSuggestionTargetValueDeserializerTest.class, fileName),
                GitHubIssueSuggestionTargetValue.class);
    }
}
