package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/** Pull request in a stack. */
@CatalogSchema("components/schemas/pull-request-stack-pull-request")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPullRequestStackPullRequest(
        long id,
        int number,
        String url,
        GitHubPullRequestMinimalRef head,
        GitHubPullRequestMinimalRef base,
        String nodeId,
        String title,
        GitHubPullRequestStackState state,
        @Nullable Instant mergedAt,
        boolean draft,
        String htmlUrl,
        @Nullable OptionalNull<GitHubSimpleUser> user) {

    public GitHubPullRequestStackPullRequest {
        requireNonNull(url, "url");
        requireNonNull(head, "head");
        requireNonNull(base, "base");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(title, "title");
        requireNonNull(state, "state");
        requireNonNull(htmlUrl, "htmlUrl");
    }
}
