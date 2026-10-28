package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotEnterpriseContentExclusionRule;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Encodes enterprise content exclusion rules as a JSON string path or as the {@code ifAnyMatch} / {@code ifNoneMatch}
 * object.
 */
final class GitHubCopilotEnterpriseContentExclusionRuleSerializer
        extends StdSerializer<GitHubCopilotEnterpriseContentExclusionRule> {

    GitHubCopilotEnterpriseContentExclusionRuleSerializer() {
        super(GitHubCopilotEnterpriseContentExclusionRule.class);
    }

    @Override
    public void serialize(
            final GitHubCopilotEnterpriseContentExclusionRule value,
            final JsonGenerator generator,
            final SerializationContext context) {
        final String path = value.path();
        if (path != null) {
            generator.writeString(path);
            return;
        }
        final GitHubCopilotEnterpriseContentExclusionRule.IfAnyMatchDetails ifAnyMatch = value.ifAnyMatch();
        if (ifAnyMatch != null) {
            context.writeValue(generator, ifAnyMatch);
            return;
        }
        context.writeValue(generator, value.ifNoneMatch());
    }
}
