package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueFieldValueWire;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubIssueFieldValueWireDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnText_whenJsonIsString() {
        final GitHubIssueFieldValueWire decoded = decode("issue-field-value-wire-text.json");

        assertThat(decoded.textValue()).isEqualTo("high");
    }

    @Test
    void decode_shouldReturnNumber_whenJsonIsInteger() {
        final GitHubIssueFieldValueWire decoded = decode("issue-field-value-wire-number.json");

        assertThat(decoded.numberValue()).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void decode_shouldReturnNumber_whenJsonIsFloat() {
        final GitHubIssueFieldValueWire decoded = decode("issue-field-value-wire-float.json");

        assertThat(decoded.numberValue()).isEqualTo(new BigDecimal("1.5"));
    }

    @Test
    void decode_shouldReturnTexts_whenJsonIsArray() {
        final GitHubIssueFieldValueWire decoded = decode("issue-field-value-wire-list.json");

        assertThat(decoded.listValue()).containsExactly("a", "b");
    }

    @Test
    void decode_shouldThrowDecodeException_whenArrayHoldsNull() {
        final byte[] json = "[\"a\",null]".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubIssueFieldValueWire.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void decode_shouldThrow_whenJsonIsBoolean() {
        final byte[] json = FileUtils.readBytes(
                GitHubIssueFieldValueWireDeserializerTest.class, "issue-field-value-wire-boolean.json");

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubIssueFieldValueWire.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("value must be");
    }

    private static GitHubIssueFieldValueWire decode(final String fileName) {
        return CODEC.decode(
                FileUtils.readBytes(GitHubIssueFieldValueWireDeserializerTest.class, fileName),
                GitHubIssueFieldValueWire.class);
    }
}
