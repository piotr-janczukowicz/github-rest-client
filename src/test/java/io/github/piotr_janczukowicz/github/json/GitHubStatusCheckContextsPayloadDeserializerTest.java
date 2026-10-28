package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.branches.GitHubStatusCheckContextsPayload;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubStatusCheckContextsPayloadDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void deserialize_shouldReturnContextList_whenJsonIsArray() {
        final GitHubStatusCheckContextsPayload decoded = decode("[\"ci\"]");

        assertThat(decoded.contextList()).containsExactly("ci");
    }

    @Test
    void deserialize_shouldReturnContexts_whenJsonIsObject() {
        final GitHubStatusCheckContextsPayload decoded = decode("{\"contexts\":[\"ci\"]}");

        assertThat(decoded.contexts()).containsExactly("ci");
    }

    @Test
    void encode_shouldWriteArray_whenContextListPresent() {
        final byte[] json = CODEC.encode(GitHubStatusCheckContextsPayload.withContextList(List.of("ci")));

        assertThat(new String(json, StandardCharsets.UTF_8)).isEqualTo("[\"ci\"]");
    }

    @Test
    void encode_shouldWriteObject_whenContextsPresent() {
        final byte[] json = CODEC.encode(GitHubStatusCheckContextsPayload.withContexts(List.of("ci")));

        assertThat(new String(json, StandardCharsets.UTF_8)).isEqualTo("{\"contexts\":[\"ci\"]}");
    }

    @Test
    void deserialize_shouldThrow_whenObjectOmitsContexts() {
        final byte[] bytes = "{}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, GitHubStatusCheckContextsPayload.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("contexts");
    }

    private static GitHubStatusCheckContextsPayload decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), GitHubStatusCheckContextsPayload.class);
    }
}
