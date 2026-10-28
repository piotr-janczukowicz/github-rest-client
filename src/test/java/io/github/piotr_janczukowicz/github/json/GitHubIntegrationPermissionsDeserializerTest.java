package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationPermissions;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.lang.reflect.RecordComponent;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubIntegrationPermissionsDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void deserialize_shouldMapNamedAndAdditional_whenJsonHasExtraKey() {
        final GitHubIntegrationPermissions decoded = decode("{\"issues\":\"read\",\"administration\":\"write\"}");

        assertSoftly(softly -> {
            softly.assertThat(decoded.issues()).isEqualTo("read");
            softly.assertThat(decoded.additionalProperties()).containsEntry("administration", "write");
        });
    }

    @Test
    void deserialize_shouldTreatExplicitNullNamedFieldAsAbsent_whenJsonHasNull() {
        final GitHubIntegrationPermissions decoded = decode("{\"issues\":null}");

        assertThat(decoded.issues()).isNull();
    }

    @Test
    void deserialize_shouldThrow_whenNamedPermissionNotString() {
        final byte[] bytes = "{\"issues\":1}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, GitHubIntegrationPermissions.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("issues");
    }

    @Test
    void deserialize_shouldThrow_whenAdditionalPermissionNotString() {
        final byte[] bytes = "{\"administration\":1}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, GitHubIntegrationPermissions.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("administration");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[\"a\",\"b\"]", "[]", "\"text\"", "5", "true"})
    void deserialize_shouldThrowDecodeException_whenValueIsNotAnObject(final String json) {
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, GitHubIntegrationPermissions.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("must be an object");
    }

    @Test
    void deserialize_shouldMapEveryNamedComponent_whenAllNamedKeysPresent() {
        final GitHubIntegrationPermissions decoded = decode(
                "{\"issues\":\"i\",\"checks\":\"c\",\"metadata\":\"m\",\"contents\":\"o\",\"deployments\":\"d\"}");

        assertSoftly(softly -> {
            softly.assertThat(decoded.issues()).isEqualTo("i");
            softly.assertThat(decoded.checks()).isEqualTo("c");
            softly.assertThat(decoded.metadata()).isEqualTo("m");
            softly.assertThat(decoded.contents()).isEqualTo("o");
            softly.assertThat(decoded.deployments()).isEqualTo("d");
            softly.assertThat(decoded.additionalProperties()).isEmpty();
        });
    }

    @Test
    void deserialize_shouldNotRouteRecordComponentsToAdditional_whenKeysMatchComponents() {
        final List<String> routed = new ArrayList<>();

        for (final RecordComponent component : GitHubIntegrationPermissions.class.getRecordComponents()) {
            if (!"additionalProperties".equals(component.getName())) {
                final GitHubIntegrationPermissions decoded = decode("{\"" + component.getName() + "\":\"x\"}");
                if (decoded.additionalProperties().containsKey(component.getName())) {
                    routed.add(component.getName());
                }
            }
        }

        assertThat(routed).isEmpty();
    }

    @Test
    void deserialize_shouldReturnEmpty_whenObjectEmpty() {
        final GitHubIntegrationPermissions decoded = decode("{}");

        assertThat(decoded).isEqualTo(new GitHubIntegrationPermissions(null, null, null, null, null, Map.of()));
    }

    @Test
    void encodeDecode_shouldRoundTrip_whenNamedAndAdditionalSet() {
        final GitHubIntegrationPermissions permissions = new GitHubIntegrationPermissions(
                "read", null, "read", null, "write", Map.of("administration", "write"));

        final GitHubIntegrationPermissions decoded =
                CODEC.decode(CODEC.encode(permissions), GitHubIntegrationPermissions.class);

        assertThat(decoded).isEqualTo(permissions);
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"issues\":\"read\",\"issues\":\"write\"}", "{\"issues\":\"read\",\"issues\":null}"})
    void deserialize_shouldThrow_whenKeyRepeats(final String json) {
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, GitHubIntegrationPermissions.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .hasMessageContaining("Duplicate Object property");
    }

    private static GitHubIntegrationPermissions decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), GitHubIntegrationPermissions.class);
    }
}
