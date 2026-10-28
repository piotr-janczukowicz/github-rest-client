package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.copilot.CopilotCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotContentExclusionRule;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotContentExclusionRuleSerializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldWriteJsonString_whenPathPresent() {
        final GitHubCopilotContentExclusionRule rule = CopilotCatalogFixtures.copilotContentExclusionRule();

        final String json = new String(CODEC.encode(rule), StandardCharsets.UTF_8);

        assertThat(json).isEqualTo(readExpected("copilot-content-exclusion-rule-path.json"));
    }

    @Test
    void encode_shouldWriteIfAnyMatchObject_whenIfAnyMatchPresent() {
        final GitHubCopilotContentExclusionRule rule = CopilotCatalogFixtures.copilotContentExclusionRuleIfAnyMatch();

        final String json = new String(CODEC.encode(rule), StandardCharsets.UTF_8);

        assertThat(json).isEqualTo(readExpected("copilot-content-exclusion-rule-if-any-match.json"));
    }

    @Test
    void encode_shouldWriteIfNoneMatchObject_whenIfNoneMatchPresent() {
        final GitHubCopilotContentExclusionRule rule = CopilotCatalogFixtures.copilotContentExclusionRuleIfNoneMatch();

        final String json = new String(CODEC.encode(rule), StandardCharsets.UTF_8);

        assertThat(json).isEqualTo(readExpected("copilot-content-exclusion-rule-if-none-match.json"));
    }

    private static String readExpected(final String fileName) {
        return FileUtils.readString(GitHubCopilotContentExclusionRuleSerializerTest.class, fileName);
    }
}
