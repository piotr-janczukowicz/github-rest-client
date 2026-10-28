package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.scim.GitHubScimOperationValue;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubScimOperationValueDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnText_whenJsonIsString() {
        final GitHubScimOperationValue value = decode("\"false\"");

        assertThat(value.text()).isEqualTo("false");
    }

    @Test
    void decode_shouldReturnObject_whenJsonIsObject() {
        final GitHubScimOperationValue value = decode("{\"active\":false}");

        assertThat(value.object()).isEqualTo(Map.of("active", false));
    }

    @Test
    void decode_shouldReturnArray_whenJsonIsArrayOfObjects() {
        final GitHubScimOperationValue value = decode("[{\"value\":\"work@example.com\",\"primary\":true}]");

        assertThat(value.array()).isEqualTo(List.of(Map.of("value", "work@example.com", "primary", true)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"42", "[\"a\"]", "{\"active\":true,\"active\":false}", "true"})
    void decode_shouldThrowDecodeException_whenJsonIsNotTextObjectOrArrayOfObjects(final String text) {
        final byte[] json = text.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubScimOperationValue.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void decode_shouldThrowDecodeException_whenArrayHoldsNull() {
        final byte[] json = "[null]".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubScimOperationValue.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasStackTraceContaining("array must hold objects only");
    }

    @Test
    void encode_shouldWriteJsonString_whenTextPresent() {
        assertThat(encode(GitHubScimOperationValue.text("false"))).isEqualTo("\"false\"");
    }

    @Test
    void encode_shouldWriteJsonObject_whenObjectPresent() {
        assertThat(encode(GitHubScimOperationValue.object(Map.of("active", false))))
                .isEqualTo("{\"active\":false}");
    }

    @Test
    void encode_shouldWriteJsonArray_whenArrayPresent() {
        final Map<String, Object> email = new LinkedHashMap<>();
        email.put("value", "work@example.com");
        email.put("primary", true);
        final GitHubScimOperationValue value = GitHubScimOperationValue.array(List.of(email));

        assertThat(encode(value)).isEqualTo("[{\"value\":\"work@example.com\",\"primary\":true}]");
    }

    private static String encode(final GitHubScimOperationValue value) {
        return new String(CODEC.encode(value), StandardCharsets.UTF_8);
    }

    private static GitHubScimOperationValue decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), GitHubScimOperationValue.class);
    }
}
