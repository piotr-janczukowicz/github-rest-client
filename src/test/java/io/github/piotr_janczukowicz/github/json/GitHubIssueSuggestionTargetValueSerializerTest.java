package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueSuggestionTargetValue;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueSuggestionTargetValueSerializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldWriteJsonString_whenTextPresent() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.text("bug");

        final String json = encode(value);

        assertThat(json).isEqualTo(readExpected("issue-suggestion-target-value-text.json"));
    }

    @Test
    void encode_shouldWriteJsonNumber_whenNumberPresent() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.number(1.5d);

        final String json = encode(value);

        assertThat(json).isEqualTo(readExpected("issue-suggestion-target-value-number.json"));
    }

    @Test
    void encode_shouldWriteJsonBoolean_whenFlagPresent() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.flag(true);

        final String json = encode(value);

        assertThat(json).isEqualTo(readExpected("issue-suggestion-target-value-flag.json"));
    }

    @Test
    void encode_shouldWriteJsonArray_whenListPresent() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.texts(List.of("a"));

        final String json = encode(value);

        assertThat(json).isEqualTo(readExpected("issue-suggestion-target-value-list.json"));
    }

    private static String encode(final Object value) {
        return new String(CODEC.encode(value), StandardCharsets.UTF_8);
    }

    private static String readExpected(final String fileName) {
        return FileUtils.readString(GitHubIssueSuggestionTargetValueSerializerTest.class, fileName);
    }
}
