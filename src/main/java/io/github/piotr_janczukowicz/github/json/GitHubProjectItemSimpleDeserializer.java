package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectDraftIssue;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemContent;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemContentType;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemSimple;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.json.jackson.BufferedJson;
import java.time.Instant;
import java.util.Map;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Decodes project items using the sibling {@code content_type} property as discriminator.
 *
 * <p>A missing {@code content_type} fails the decode with a
 * {@link io.github.piotr_janczukowicz.json.JsonDecodeException}; there is no value to return in its place. An unknown
 * one decodes as {@link GitHubProjectItemContentType#UNKNOWN} with the content kept as a
 * {@link GitHubProjectItemContent.Unknown}.
 */
final class GitHubProjectItemSimpleDeserializer extends StdDeserializer<GitHubProjectItemSimple> {

    private static final String CONTENT = "content";

    GitHubProjectItemSimpleDeserializer() {
        super(GitHubProjectItemSimple.class);
    }

    @Override
    public GitHubProjectItemSimple deserialize(final JsonParser parser, final DeserializationContext context) {
        try (BufferedJson json = BufferedJson.capture(parser, context)) {
            final Binding binding = json.read(Binding.class);
            final Long id = binding.id();
            if (id == null) {
                throw json.mismatch("Missing project item id");
            }
            final GitHubProjectItemContentType contentType = binding.contentType();
            if (contentType == null) {
                throw json.mismatch("Missing project item content_type");
            }
            final Instant createdAt = binding.createdAt();
            final Instant updatedAt = binding.updatedAt();
            if (createdAt == null || updatedAt == null) {
                throw json.mismatch("Missing project item created_at or updated_at");
            }
            final GitHubProjectItemContent content = json.hasNonNull(CONTENT)
                    ? wrapContent(contentType, json.readRequiredField(CONTENT, contentType.contentValueType()))
                    : null;
            return new GitHubProjectItemSimple(
                    id,
                    binding.nodeId(),
                    content,
                    contentType,
                    binding.creator(),
                    createdAt,
                    updatedAt,
                    binding.archivedAt(),
                    binding.projectUrl(),
                    binding.itemUrl());
        }
    }

    private static GitHubProjectItemContent wrapContent(
            final GitHubProjectItemContentType contentType, final Object value) {
        return switch (contentType) {
            case ISSUE -> new GitHubProjectItemContent.Issue((GitHubIssue) value);
            case PULL_REQUEST -> new GitHubProjectItemContent.PullRequest((GitHubPullRequestSimple) value);
            case DRAFT_ISSUE -> new GitHubProjectItemContent.DraftIssue((GitHubProjectDraftIssue) value);
            case UNKNOWN -> new GitHubProjectItemContent.Unknown(properties(value));
        };
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> properties(final Object value) {
        return (Map<String, Object>) value;
    }

    private record Binding(
            @Nullable Long id,
            @Nullable String nodeId,
            @Nullable GitHubProjectItemContentType contentType,
            @Nullable GitHubSimpleUser creator,
            @Nullable Instant createdAt,
            @Nullable Instant updatedAt,
            @Nullable OptionalNull<Instant> archivedAt,
            @Nullable String projectUrl,
            @Nullable String itemUrl) {}
}
