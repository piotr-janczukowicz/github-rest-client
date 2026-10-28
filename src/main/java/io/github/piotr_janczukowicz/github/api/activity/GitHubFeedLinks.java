package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Links of the feed.
 *
 * @param timeline Timeline link
 * @param user User link
 * @param securityAdvisories Security advisories link
 * @param currentUser Current user link
 * @param currentUserPublic Current user public link
 * @param currentUserActor Current user actor link
 * @param currentUserOrganization Current user organization link
 * @param currentUserOrganizations Current user organization links
 * @param repositoryDiscussions Repository discussions link
 * @param repositoryDiscussionsCategory Repository discussions category link
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/feed/properties/_links")
@RecordBuilder
public record GitHubFeedLinks(
        GitHubLinkWithType timeline,
        GitHubLinkWithType user,
        @Nullable GitHubLinkWithType securityAdvisories,
        @Nullable GitHubLinkWithType currentUser,
        @Nullable GitHubLinkWithType currentUserPublic,
        @Nullable GitHubLinkWithType currentUserActor,
        @Nullable GitHubLinkWithType currentUserOrganization,
        @Nullable List<GitHubLinkWithType> currentUserOrganizations,
        @Nullable GitHubLinkWithType repositoryDiscussions,
        @Nullable GitHubLinkWithType repositoryDiscussionsCategory) {

    public GitHubFeedLinks {
        requireNonNull(timeline, "timeline");
        requireNonNull(user, "user");
    }
}
