package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubSetCopilotTeamCustomizationsPayloadTest {

    @Test
    void gitHubSetCopilotTeamCustomizationsPayload_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubSetCopilotTeamCustomizationsPayload value =
                CopilotCatalogFixtures.setCopilotTeamCustomizationsPayload();

        assertSoftly(softly -> {
            softly.assertThat(value.pluginAssignments())
                    .isEqualTo(Map.of(
                            "code-review@acme-marketplace",
                            CopilotCatalogFixtures.setCopilotTeamCustomizationsPluginAssignment()));
            softly.assertThat(value.extraKnownMarketplaces())
                    .isEqualTo(Map.of("acme-marketplace", Map.of("source", "github")));
            softly.assertThat(value.additionalPropertiesForJson())
                    .containsExactly(Map.entry("futureSetting", "enabled"));
        });
    }

    @Test
    void constructor_shouldThrow_whenAdditionalPropertiesNull() {
        final GitHubSetCopilotTeamCustomizationsPayloadBuilder builder =
                GitHubSetCopilotTeamCustomizationsPayloadBuilder.builder(
                                CopilotCatalogFixtures.setCopilotTeamCustomizationsPayload())
                        .additionalProperties(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("additionalProperties");
    }

    @Test
    void constructor_shouldAcceptNull_whenMapsNull() {
        final GitHubSetCopilotTeamCustomizationsPayload value =
                GitHubSetCopilotTeamCustomizationsPayloadBuilder.builder()
                        .additionalProperties(Map.of())
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(value.pluginAssignments()).isNull();
            softly.assertThat(value.extraKnownMarketplaces()).isNull();
            softly.assertThat(value.additionalPropertiesForJson()).isEmpty();
        });
    }

    @Test
    void constructor_shouldCopy_whenMapsGiven() {
        final Map<String, GitHubSetCopilotTeamCustomizationsPluginAssignment> plugins = new HashMap<>();
        plugins.put(
                "code-review@acme-marketplace", CopilotCatalogFixtures.setCopilotTeamCustomizationsPluginAssignment());
        final Map<String, Map<String, Object>> marketplaces = new HashMap<>();
        marketplaces.put("acme-marketplace", Map.of("source", "github"));
        final Map<String, Object> additional = new HashMap<>();
        additional.put("futureSetting", "enabled");
        final GitHubSetCopilotTeamCustomizationsPayload value =
                GitHubSetCopilotTeamCustomizationsPayloadBuilder.builder()
                        .pluginAssignments(plugins)
                        .extraKnownMarketplaces(marketplaces)
                        .additionalProperties(additional)
                        .build();

        plugins.clear();
        marketplaces.clear();
        additional.clear();

        assertSoftly(softly -> {
            softly.assertThat(value.pluginAssignments()).hasSize(1);
            softly.assertThat(value.extraKnownMarketplaces()).hasSize(1);
            softly.assertThat(value.additionalPropertiesForJson()).hasSize(1);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"pluginAssignments", "extraKnownMarketplaces"})
    void constructor_shouldThrow_whenAdditionalPropertiesRepeatNamedKey(final String key) {
        final GitHubSetCopilotTeamCustomizationsPayloadBuilder builder =
                GitHubSetCopilotTeamCustomizationsPayloadBuilder.builder(
                                CopilotCatalogFixtures.setCopilotTeamCustomizationsPayload())
                        .additionalProperties(Map.of(key, "x"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(key);
    }
}
