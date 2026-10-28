package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotContentExclusionRule;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotContentExclusionRule.IfAnyMatchDetails;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotContentExclusionRule.IfNoneMatchDetails;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes content exclusion rules from a JSON string path, an {@code ifAnyMatch} object, or an {@code ifNoneMatch}
 * object.
 */
final class GitHubCopilotContentExclusionRuleDeserializer extends StdDeserializer<GitHubCopilotContentExclusionRule> {

    GitHubCopilotContentExclusionRuleDeserializer() {
        super(GitHubCopilotContentExclusionRule.class);
    }

    @Override
    public GitHubCopilotContentExclusionRule deserialize(
            final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = context.readValue(parser, JsonNode.class);
        return ContentExclusionRules.decode(
                node,
                context,
                GitHubCopilotContentExclusionRule::ofPath,
                paths -> GitHubCopilotContentExclusionRule.ofIfAnyMatch(new IfAnyMatchDetails(paths)),
                paths -> GitHubCopilotContentExclusionRule.ofIfNoneMatch(new IfNoneMatchDetails(paths)));
    }
}
