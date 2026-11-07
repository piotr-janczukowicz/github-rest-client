package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.copilot.GitHubGetCopilotTeamCustomizationsPluginAssignment;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubGetCopilotTeamCustomizationsPluginAssignmentState;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubGetCopilotTeamCustomizationsResponse;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubSetCopilotTeamCustomizationsPayload;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubSetCopilotTeamCustomizationsPluginAssignment;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubSetCopilotTeamCustomizationsPluginAssignmentState;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubSetCopilotTeamCustomizationsResponse;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubSetCopilotTeamCustomizationsResponsePluginAssignment;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubSetCopilotTeamCustomizationsResponsePluginAssignmentState;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotTeamCustomizationsDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();
    private static final String FULL = "{\"pluginAssignments\":{\"code-review@acme\":{\"state\":\"defaultEnabled\"}},"
            + "\"extraKnownMarketplaces\":{\"acme\":{\"source\":\"github\"}},\"futureSetting\":\"enabled\"}";

    @Test
    void deserialize_shouldMapNamedAndAdditional_whenGetResponseHasExtraKey() {
        final GitHubGetCopilotTeamCustomizationsResponse decoded =
                decode(FULL, GitHubGetCopilotTeamCustomizationsResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.pluginAssignments())
                    .containsExactly(Map.entry(
                            "code-review@acme",
                            new GitHubGetCopilotTeamCustomizationsPluginAssignment(
                                    GitHubGetCopilotTeamCustomizationsPluginAssignmentState.DEFAULT_ENABLED)));
            softly.assertThat(decoded.extraKnownMarketplaces())
                    .containsExactly(Map.entry("acme", Map.of("source", "github")));
            softly.assertThat(decoded.additionalPropertiesForJson())
                    .containsExactly(Map.entry("futureSetting", "enabled"));
        });
    }

    @Test
    void deserialize_shouldMapNamedAndAdditional_whenPayloadHasExtraKey() {
        final GitHubSetCopilotTeamCustomizationsPayload decoded =
                decode(FULL, GitHubSetCopilotTeamCustomizationsPayload.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.pluginAssignments())
                    .containsExactly(Map.entry(
                            "code-review@acme",
                            new GitHubSetCopilotTeamCustomizationsPluginAssignment(
                                    GitHubSetCopilotTeamCustomizationsPluginAssignmentState.DEFAULT_ENABLED)));
            softly.assertThat(decoded.additionalPropertiesForJson())
                    .containsExactly(Map.entry("futureSetting", "enabled"));
        });
    }

    @Test
    void deserialize_shouldKeepNullValues_whenPayloadHasNullAdditionalAndMarketplaceValue() {
        final GitHubSetCopilotTeamCustomizationsPayload decoded = decode(
                "{\"extraKnownMarketplaces\":{\"acme\":null},\"futureSetting\":null}",
                GitHubSetCopilotTeamCustomizationsPayload.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.extraKnownMarketplaces()).containsEntry("acme", null);
            softly.assertThat(decoded.additionalPropertiesForJson()).containsEntry("futureSetting", null);
        });
    }

    @Test
    void deserialize_shouldMapNamedAndAdditional_whenSetResponseHasExtraKey() {
        final GitHubSetCopilotTeamCustomizationsResponse decoded =
                decode(FULL, GitHubSetCopilotTeamCustomizationsResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.pluginAssignments())
                    .containsExactly(Map.entry(
                            "code-review@acme",
                            new GitHubSetCopilotTeamCustomizationsResponsePluginAssignment(
                                    GitHubSetCopilotTeamCustomizationsResponsePluginAssignmentState.DEFAULT_ENABLED)));
            softly.assertThat(decoded.additionalPropertiesForJson())
                    .containsExactly(Map.entry("futureSetting", "enabled"));
        });
    }

    @Test
    void deserialize_shouldReturnEmptyComponents_whenObjectEmpty() {
        final GitHubGetCopilotTeamCustomizationsResponse decoded =
                decode("{}", GitHubGetCopilotTeamCustomizationsResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.pluginAssignments()).isNull();
            softly.assertThat(decoded.extraKnownMarketplaces()).isNull();
            softly.assertThat(decoded.additionalPropertiesForJson()).isEmpty();
        });
    }

    @Test
    void deserialize_shouldTreatExplicitNullNamedFieldAsAbsent_whenJsonHasNull() {
        final GitHubGetCopilotTeamCustomizationsResponse decoded = decode(
                "{\"pluginAssignments\":null,\"extraKnownMarketplaces\":null}",
                GitHubGetCopilotTeamCustomizationsResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded.pluginAssignments()).isNull();
            softly.assertThat(decoded.extraKnownMarketplaces()).isNull();
            softly.assertThat(decoded.additionalPropertiesForJson()).isEmpty();
        });
    }

    @Test
    void deserialize_shouldThrow_whenPluginAssignmentsNotAnObject() {
        final byte[] bytes = "{\"pluginAssignments\":1}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown =
                catchThrowable(() -> CODEC.decode(bytes, GitHubGetCopilotTeamCustomizationsResponse.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void encodeDecode_shouldRoundTrip_whenNamedAndAdditionalSet() {
        final GitHubGetCopilotTeamCustomizationsResponse decoded =
                decode(FULL, GitHubGetCopilotTeamCustomizationsResponse.class);

        final GitHubGetCopilotTeamCustomizationsResponse roundTripped =
                CODEC.decode(CODEC.encode(decoded), GitHubGetCopilotTeamCustomizationsResponse.class);

        assertThat(roundTripped).isEqualTo(decoded);
    }

    private static <T> T decode(final String json, final Class<T> type) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), type);
    }
}
