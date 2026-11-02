package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAvailableRepoAutomationTriggersItemFieldSchema;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAvailableRepoAutomationTriggersItemFieldSchemaInteger;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAvailableRepoAutomationTriggersItemFieldSchemaString;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAvailableRepoAutomationTriggersItemFieldSchemaUnknown;
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
                "{\"type\":\"string\"}|GitHubListAvailableRepoAutomationTriggersItemFieldSchemaString",
                "{\"type\":\"array\",\"items\":{\"type\":\"string\"}}|GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArray",
                "{\"type\":\"integer\"}|GitHubListAvailableRepoAutomationTriggersItemFieldSchemaInteger"
            })
    void decode_shouldReturnTheKnownVariant_whenTypeIsKnown(final String json, final String variant)
            throws ClassNotFoundException {
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final GitHubListAvailableRepoAutomationTriggersItemFieldSchema decoded =
                CODEC.decode(bytes, GitHubListAvailableRepoAutomationTriggersItemFieldSchema.class);

        assertThat(decoded)
                .isInstanceOf(
                        Class.forName(GitHubListAvailableRepoAutomationTriggersItemFieldSchema.class.getPackageName()
                                + "." + variant));
    }

    @Test
    void decode_shouldReturnUnknown_whenTypeIsNotKnown() {
        final byte[] bytes = "{\"type\":\"boolean\",\"default\":true}".getBytes(StandardCharsets.UTF_8);

        final GitHubListAvailableRepoAutomationTriggersItemFieldSchema decoded =
                CODEC.decode(bytes, GitHubListAvailableRepoAutomationTriggersItemFieldSchema.class);

        assertThat(decoded)
                .isEqualTo(new GitHubListAvailableRepoAutomationTriggersItemFieldSchemaUnknown(
                        Map.of("type", "boolean", "default", true)));
    }

    @Test
    void decode_shouldKeepTheOtherSchemas_whenOneListItemHasAnUnknownType() {
        final byte[] bytes =
                "[{\"type\":\"string\"},{\"type\":\"object\"},{\"type\":\"integer\"}]".getBytes(StandardCharsets.UTF_8);

        final List<GitHubListAvailableRepoAutomationTriggersItemFieldSchema> decoded =
                CODEC.decode(bytes, JsonTypes.listOf(GitHubListAvailableRepoAutomationTriggersItemFieldSchema.class));

        assertThat(decoded)
                .hasSize(3)
                .hasOnlyElementsOfTypes(
                        GitHubListAvailableRepoAutomationTriggersItemFieldSchemaString.class,
                        GitHubListAvailableRepoAutomationTriggersItemFieldSchemaUnknown.class,
                        GitHubListAvailableRepoAutomationTriggersItemFieldSchemaInteger.class);
    }
}
