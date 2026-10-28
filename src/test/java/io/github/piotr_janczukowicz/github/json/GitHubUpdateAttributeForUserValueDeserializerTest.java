package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.scim.GitHubUpdateAttributeForUserValue;
import io.github.piotr_janczukowicz.github.api.scim.GitHubUpdateAttributeForUserValueEmail;
import io.github.piotr_janczukowicz.github.api.scim.GitHubUpdateAttributeForUserValueObject;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubUpdateAttributeForUserValueDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnText_whenJsonIsString() {
        final GitHubUpdateAttributeForUserValue value = decode("\"false\"");

        assertThat(value.text()).isEqualTo("false");
    }

    @Test
    void decode_shouldReturnObject_whenJsonIsNamedAttributes() {
        final GitHubUpdateAttributeForUserValue value = decode("{\"active\":false,\"userName\":\"octocat\"}");

        assertThat(value.object().active()).isEqualTo(OptionalNull.of(false));
        assertThat(value.object().userName()).isEqualTo(OptionalNull.of("octocat"));
    }

    @Test
    void decode_shouldReturnArray_whenJsonIsArrayOfEmails() {
        final GitHubUpdateAttributeForUserValue value =
                decode("[{\"value\":\"mona.octocat@okta.example.com\",\"primary\":true}]");

        assertThat(value.array()).hasSize(1);
        assertThat(value.array().getFirst().value()).isEqualTo("mona.octocat@okta.example.com");
    }

    @ParameterizedTest
    @ValueSource(strings = {"42", "[\"a\"]", "{\"active\":true,\"active\":false}", "true"})
    void decode_shouldThrowDecodeException_whenJsonIsNotTextObjectOrArrayOfObjects(final String text) {
        final byte[] json = text.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubUpdateAttributeForUserValue.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void decode_shouldThrowDecodeException_whenArrayHoldsNull() {
        final byte[] json = "[null]".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubUpdateAttributeForUserValue.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasStackTraceContaining("array must hold objects only");
    }

    @Test
    void encode_shouldWriteJsonString_whenTextPresent() {
        assertThat(encode(GitHubUpdateAttributeForUserValue.text("false"))).isEqualTo("\"false\"");
    }

    @Test
    void encode_shouldWriteJsonObject_whenObjectPresent() {
        final GitHubUpdateAttributeForUserValueObject object = new GitHubUpdateAttributeForUserValueObject(
                OptionalNull.of(false), OptionalNull.of("octocat"), null, null, null);

        assertThat(encode(GitHubUpdateAttributeForUserValue.object(object)))
                .isEqualTo("{\"active\":false,\"userName\":\"octocat\"}");
    }

    @Test
    void encode_shouldWriteExplicitNull_whenAttributeIsOptionalNullOfNull() {
        final GitHubUpdateAttributeForUserValueObject object =
                new GitHubUpdateAttributeForUserValueObject(null, null, null, OptionalNull.ofNull(), null);

        assertThat(encode(GitHubUpdateAttributeForUserValue.object(object))).isEqualTo("{\"givenName\":null}");
    }

    @Test
    void decode_shouldKeepExplicitNull_whenAttributeIsJsonNull() {
        final byte[] json = "{\"givenName\":null}".getBytes(StandardCharsets.UTF_8);

        final GitHubUpdateAttributeForUserValue value = CODEC.decode(json, GitHubUpdateAttributeForUserValue.class);

        assertThat(value.object()).isNotNull();
        assertThat(value.object().givenName()).isEqualTo(OptionalNull.ofNull());
        assertThat(value.object().familyName()).isNull();
    }

    @Test
    void encode_shouldWriteJsonArray_whenArrayPresent() {
        final GitHubUpdateAttributeForUserValueEmail email =
                new GitHubUpdateAttributeForUserValueEmail("octocat@example.com", true);

        assertThat(encode(GitHubUpdateAttributeForUserValue.array(List.of(email))))
                .isEqualTo("[{\"value\":\"octocat@example.com\",\"primary\":true}]");
    }

    private static String encode(final GitHubUpdateAttributeForUserValue value) {
        return new String(CODEC.encode(value), StandardCharsets.UTF_8);
    }

    private static GitHubUpdateAttributeForUserValue decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), GitHubUpdateAttributeForUserValue.class);
    }
}
