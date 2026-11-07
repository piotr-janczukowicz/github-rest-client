package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubSetCopilotTeamCustomizationsResponseTest {

    @Test
    void gitHubSetCopilotTeamCustomizationsResponse_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubSetCopilotTeamCustomizationsResponse value =
                CopilotCatalogFixtures.setCopilotTeamCustomizationsResponse();

        assertSoftly(softly -> {
            softly.assertThat(value.pluginAssignments())
                    .isEqualTo(Map.of(
                            "code-review@acme-marketplace",
                            CopilotCatalogFixtures.setCopilotTeamCustomizationsResponsePluginAssignment()));
            softly.assertThat(value.extraKnownMarketplaces())
                    .isEqualTo(Map.of("acme-marketplace", Map.of("source", "github")));
            softly.assertThat(value.additionalPropertiesForJson())
                    .containsExactly(Map.entry("futureSetting", "enabled"));
        });
    }

    @Test
    void constructor_shouldThrow_whenAdditionalPropertiesNull() {
        final GitHubSetCopilotTeamCustomizationsResponseBuilder builder =
                GitHubSetCopilotTeamCustomizationsResponseBuilder.builder(
                                CopilotCatalogFixtures.setCopilotTeamCustomizationsResponse())
                        .additionalProperties(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("additionalProperties");
    }

    @Test
    void constructor_shouldAcceptNull_whenMapsNull() {
        final GitHubSetCopilotTeamCustomizationsResponse value =
                GitHubSetCopilotTeamCustomizationsResponseBuilder.builder()
                        .additionalProperties(Map.of())
                        .build();

        assertSoftly(softly -> {
            softly.assertThat(value.pluginAssignments()).isNull();
            softly.assertThat(value.extraKnownMarketplaces()).isNull();
            softly.assertThat(value.additionalPropertiesForJson()).isEmpty();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"pluginAssignments", "extraKnownMarketplaces"})
    void constructor_shouldThrow_whenAdditionalPropertiesRepeatNamedKey(final String key) {
        final GitHubSetCopilotTeamCustomizationsResponseBuilder builder =
                GitHubSetCopilotTeamCustomizationsResponseBuilder.builder(
                                CopilotCatalogFixtures.setCopilotTeamCustomizationsResponse())
                        .additionalProperties(Map.of(key, "x"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(key);
    }
}
