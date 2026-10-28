package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotEnterpriseContentExclusionRule;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotEnterpriseContentExclusionRule.IfAnyMatchDetails;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotEnterpriseContentExclusionRule.IfNoneMatchDetails;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes enterprise content exclusion rules from a JSON string path, an {@code ifAnyMatch} object, or an
 * {@code ifNoneMatch} object.
 */
final class GitHubCopilotEnterpriseContentExclusionRuleDeserializer
        extends StdDeserializer<GitHubCopilotEnterpriseContentExclusionRule> {

    GitHubCopilotEnterpriseContentExclusionRuleDeserializer() {
        super(GitHubCopilotEnterpriseContentExclusionRule.class);
    }

    @Override
    public GitHubCopilotEnterpriseContentExclusionRule deserialize(
            final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = context.readValue(parser, JsonNode.class);
        return ContentExclusionRules.decode(
                node,
                context,
                GitHubCopilotEnterpriseContentExclusionRule::ofPath,
                paths -> GitHubCopilotEnterpriseContentExclusionRule.ofIfAnyMatch(new IfAnyMatchDetails(paths)),
                paths -> GitHubCopilotEnterpriseContentExclusionRule.ofIfNoneMatch(new IfNoneMatchDetails(paths)));
    }
}
