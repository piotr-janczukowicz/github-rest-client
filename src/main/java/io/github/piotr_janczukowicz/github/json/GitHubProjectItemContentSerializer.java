package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemContent;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Encodes project item content as the inner issue, pull request, or draft issue object, or the properties of unknown
 * content as sent.
 */
final class GitHubProjectItemContentSerializer extends StdSerializer<GitHubProjectItemContent> {

    GitHubProjectItemContentSerializer() {
        super(GitHubProjectItemContent.class);
    }

    @Override
    public void serialize(
            final GitHubProjectItemContent value, final JsonGenerator generator, final SerializationContext context) {
        switch (value) {
            case GitHubProjectItemContent.Issue(final var issue) -> context.writeValue(generator, issue);
            case GitHubProjectItemContent.PullRequest(final var pullRequest) ->
                context.writeValue(generator, pullRequest);
            case GitHubProjectItemContent.DraftIssue(final var draft) -> context.writeValue(generator, draft);
            case GitHubProjectItemContent.Unknown(final var properties) -> context.writeValue(generator, properties);
        }
    }
}
