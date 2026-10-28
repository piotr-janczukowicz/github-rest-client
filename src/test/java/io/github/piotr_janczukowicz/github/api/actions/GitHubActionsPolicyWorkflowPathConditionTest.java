package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsPolicyWorkflowPathConditionTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldOmitWorkflowPath_whenNotSet() {
        final GitHubActionsPolicyWorkflowPathCondition condition = new GitHubActionsPolicyWorkflowPathCondition(null);

        final String json = new String(CODEC.encode(condition), StandardCharsets.UTF_8);

        assertThat(json).isEqualTo("{}");
    }

    @Test
    void encode_shouldWriteWorkflowPath_whenSet() {
        final GitHubActionsPolicyWorkflowPathCondition condition =
                ActionsCatalogFixtures.actionsPolicyWorkflowPathCondition();

        final String json = new String(CODEC.encode(condition), StandardCharsets.UTF_8);

        assertThat(json).contains("\"workflow_path\"");
    }
}
