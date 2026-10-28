package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAvailableAutomationTriggersItemFieldSchema;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAvailableAutomationTriggersItemFieldSchemaInteger;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAvailableAutomationTriggersItemFieldSchemaString;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAvailableAutomationTriggersItemFieldSchemaUnknown;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class AutomationTriggerFieldSchemaDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @ParameterizedTest
    @CsvSource(
            delimiter = '|',
            value = {
                "{\"type\":\"string\"}|GitHubListAvailableAutomationTriggersItemFieldSchemaString",
                "{\"type\":\"array\",\"items\":{\"type\":\"string\"}}|GitHubListAvailableAutomationTriggersItemFieldSchemaArray",
                "{\"type\":\"integer\"}|GitHubListAvailableAutomationTriggersItemFieldSchemaInteger"
            })
    void decode_shouldReturnTheKnownVariant_whenTypeIsKnown(final String json, final String variant)
            throws ClassNotFoundException {
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final GitHubListAvailableAutomationTriggersItemFieldSchema decoded =
                CODEC.decode(bytes, GitHubListAvailableAutomationTriggersItemFieldSchema.class);

        assertThat(decoded)
                .isInstanceOf(Class.forName(
                        GitHubListAvailableAutomationTriggersItemFieldSchema.class.getPackageName() + "." + variant));
    }

    @Test
    void decode_shouldReturnUnknown_whenTypeIsNotKnown() {
        final byte[] bytes = "{\"type\":\"boolean\",\"default\":true}".getBytes(StandardCharsets.UTF_8);

        final GitHubListAvailableAutomationTriggersItemFieldSchema decoded =
                CODEC.decode(bytes, GitHubListAvailableAutomationTriggersItemFieldSchema.class);

        assertThat(decoded)
                .isEqualTo(new GitHubListAvailableAutomationTriggersItemFieldSchemaUnknown(
                        Map.of("type", "boolean", "default", true)));
    }

    @Test
    void decode_shouldKeepTheOtherSchemas_whenOneListItemHasAnUnknownType() {
        final byte[] bytes =
                "[{\"type\":\"string\"},{\"type\":\"object\"},{\"type\":\"integer\"}]".getBytes(StandardCharsets.UTF_8);

        final List<GitHubListAvailableAutomationTriggersItemFieldSchema> decoded =
                CODEC.decode(bytes, JsonTypes.listOf(GitHubListAvailableAutomationTriggersItemFieldSchema.class));

        assertThat(decoded)
                .hasSize(3)
                .hasOnlyElementsOfTypes(
                        GitHubListAvailableAutomationTriggersItemFieldSchemaString.class,
                        GitHubListAvailableAutomationTriggersItemFieldSchemaUnknown.class,
                        GitHubListAvailableAutomationTriggersItemFieldSchemaInteger.class);
    }
}
