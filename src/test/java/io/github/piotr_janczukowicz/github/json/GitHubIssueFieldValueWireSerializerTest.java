package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueFieldValueWire;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueFieldValueWireSerializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldWriteJsonString_whenTextPresent() {
        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.text("high");

        final String json = encode(value);

        assertThat(json).isEqualTo(readExpected("issue-field-value-wire-text.json"));
    }

    @Test
    void encode_shouldWriteJsonNumber_whenNumberPresent() {
        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.number(2.0d);

        final String json = encode(value);

        assertThat(json).isEqualTo(readExpected("issue-field-value-wire-number.json"));
    }

    @Test
    void encode_shouldWriteJsonArray_whenListPresent() {
        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.texts(List.of("a", "b"));

        final String json = encode(value);

        assertThat(json).isEqualTo(readExpected("issue-field-value-wire-list.json"));
    }

    private static String encode(final Object value) {
        return new String(CODEC.encode(value), StandardCharsets.UTF_8);
    }

    private static String readExpected(final String fileName) {
        return FileUtils.readString(GitHubIssueFieldValueWireSerializerTest.class, fileName);
    }
}
