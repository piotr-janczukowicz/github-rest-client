package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotContentExclusionRule;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/** Encodes content exclusion rules as a JSON string path or as the {@code ifAnyMatch} / {@code ifNoneMatch} object. */
final class GitHubCopilotContentExclusionRuleSerializer extends StdSerializer<GitHubCopilotContentExclusionRule> {

    GitHubCopilotContentExclusionRuleSerializer() {
        super(GitHubCopilotContentExclusionRule.class);
    }

    @Override
    public void serialize(
            final GitHubCopilotContentExclusionRule value,
            final JsonGenerator generator,
            final SerializationContext context) {
        final String path = value.path();
        if (path != null) {
            generator.writeString(path);
            return;
        }
        final GitHubCopilotContentExclusionRule.IfAnyMatchDetails ifAnyMatch = value.ifAnyMatch();
        if (ifAnyMatch != null) {
            context.writeValue(generator, ifAnyMatch);
            return;
        }
        context.writeValue(generator, value.ifNoneMatch());
    }
}
