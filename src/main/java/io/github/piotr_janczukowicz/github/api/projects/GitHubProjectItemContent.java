package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;

/** Content represented by a project item. */
@CatalogSchema("components/schemas/projects-v2-item-simple/properties/content")
public sealed interface GitHubProjectItemContent
        permits GitHubProjectItemContent.Issue,
                GitHubProjectItemContent.PullRequest,
                GitHubProjectItemContent.DraftIssue,
                GitHubProjectItemContent.Unknown {

    /**
     * Issue content.
     *
     * @param issue Issue
     */
    record Issue(GitHubIssue issue) implements GitHubProjectItemContent {

        public Issue {
            requireNonNull(issue, "issue");
        }
    }

    /**
     * Pull request content.
     *
     * @param pullRequest Pull request
     */
    record PullRequest(GitHubPullRequestSimple pullRequest) implements GitHubProjectItemContent {

        public PullRequest {
            requireNonNull(pullRequest, "pullRequest");
        }
    }

    /**
     * Draft issue content.
     *
     * @param draft Draft issue
     */
    record DraftIssue(GitHubProjectDraftIssue draft) implements GitHubProjectItemContent {

        public DraftIssue {
            requireNonNull(draft, "draft");
        }
    }

    /**
     * Content of a type this library version does not know ({@link GitHubProjectItemContentType#UNKNOWN}), kept as
     * GitHub sent it so that one new content type does not fail the whole item or the page it is in.
     *
     * <p>The map is a shallow, unmodifiable copy in JSON order; nested objects and arrays are the plain maps and lists
     * that were decoded. It is not part of the OpenAPI schema: a content type that has its own record is never decoded
     * into this one.
     *
     * @param properties the properties of the content object, by name
     */
    record Unknown(@JsonValue Map<String, Object> properties) implements GitHubProjectItemContent {

        public Unknown {
            requireNonNull(properties, "properties");
        }
    }
}
