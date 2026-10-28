package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Feed. Response of the feeds endpoint, listing the URLs of the feeds available to the authenticated user.
 *
 * @param timelineUrl Timeline url
 * @param userUrl User url
 * @param currentUserPublicUrl Current user public url
 * @param currentUserUrl Current user url
 * @param currentUserActorUrl Current user actor url
 * @param currentUserOrganizationUrl Current user organization url
 * @param currentUserOrganizationUrls Current user organization urls
 * @param securityAdvisoriesUrl Security advisories url
 * @param repositoryDiscussionsUrl Repository discussions url
 * @param repositoryDiscussionsCategoryUrl Repository discussions category url
 * @param links Links
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/feed")
@RecordBuilder
public record GitHubFeed(
        String timelineUrl,
        String userUrl,
        @Nullable String currentUserPublicUrl,
        @Nullable String currentUserUrl,
        @Nullable String currentUserActorUrl,
        @Nullable String currentUserOrganizationUrl,
        @Nullable List<String> currentUserOrganizationUrls,
        @Nullable String securityAdvisoriesUrl,
        @Nullable String repositoryDiscussionsUrl,
        @Nullable String repositoryDiscussionsCategoryUrl,
        @JsonProperty("_links") GitHubFeedLinks links) {

    public GitHubFeed {
        requireNonNull(timelineUrl, "timelineUrl");
        requireNonNull(userUrl, "userUrl");
        requireNonNull(links, "links");
    }
}
