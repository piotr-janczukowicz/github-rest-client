package io.github.piotr_janczukowicz.github.api.meta;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Response of {@code GET /}: the URL of each top-level REST resource, which a client can follow instead of building
 * paths itself.
 *
 * <p>Components are the URLs as GitHub sends them and may contain RFC 6570 template expansions such as {@code {user}}
 * or {@code {?q,page}}; {@code topicSearchUrl} is the only one that may be missing. A missing non-nullable URL fails
 * decoding.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/root")
public record GitHubApiRoot(
        String currentUserUrl,
        String currentUserAuthorizationsHtmlUrl,
        String codeSearchUrl,
        String commitSearchUrl,
        String emailsUrl,
        String emojisUrl,
        String eventsUrl,
        String feedsUrl,
        String followersUrl,
        String followingUrl,
        String gistsUrl,
        String issueSearchUrl,
        String issuesUrl,
        String keysUrl,
        String labelSearchUrl,
        String notificationsUrl,
        String organizationUrl,
        String organizationRepositoriesUrl,
        String organizationTeamsUrl,
        String publicGistsUrl,
        String rateLimitUrl,
        String repositoryUrl,
        String repositorySearchUrl,
        String currentUserRepositoriesUrl,
        String starredUrl,
        String starredGistsUrl,
        @Nullable String topicSearchUrl,
        String userUrl,
        String userOrganizationsUrl,
        String userRepositoriesUrl,
        String userSearchUrl) {

    public GitHubApiRoot {
        requireNonNull(currentUserUrl, "currentUserUrl");
        requireNonNull(currentUserAuthorizationsHtmlUrl, "currentUserAuthorizationsHtmlUrl");
        requireNonNull(codeSearchUrl, "codeSearchUrl");
        requireNonNull(commitSearchUrl, "commitSearchUrl");
        requireNonNull(emailsUrl, "emailsUrl");
        requireNonNull(emojisUrl, "emojisUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(feedsUrl, "feedsUrl");
        requireNonNull(followersUrl, "followersUrl");
        requireNonNull(followingUrl, "followingUrl");
        requireNonNull(gistsUrl, "gistsUrl");
        requireNonNull(issueSearchUrl, "issueSearchUrl");
        requireNonNull(issuesUrl, "issuesUrl");
        requireNonNull(keysUrl, "keysUrl");
        requireNonNull(labelSearchUrl, "labelSearchUrl");
        requireNonNull(notificationsUrl, "notificationsUrl");
        requireNonNull(organizationUrl, "organizationUrl");
        requireNonNull(organizationRepositoriesUrl, "organizationRepositoriesUrl");
        requireNonNull(organizationTeamsUrl, "organizationTeamsUrl");
        requireNonNull(publicGistsUrl, "publicGistsUrl");
        requireNonNull(rateLimitUrl, "rateLimitUrl");
        requireNonNull(repositoryUrl, "repositoryUrl");
        requireNonNull(repositorySearchUrl, "repositorySearchUrl");
        requireNonNull(currentUserRepositoriesUrl, "currentUserRepositoriesUrl");
        requireNonNull(starredUrl, "starredUrl");
        requireNonNull(starredGistsUrl, "starredGistsUrl");
        requireNonNull(userUrl, "userUrl");
        requireNonNull(userOrganizationsUrl, "userOrganizationsUrl");
        requireNonNull(userRepositoriesUrl, "userRepositoriesUrl");
        requireNonNull(userSearchUrl, "userSearchUrl");
    }
}
