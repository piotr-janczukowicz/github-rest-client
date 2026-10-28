package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserType;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * User Search Result Item.
 *
 * @param login Login
 * @param id Id
 * @param nodeId Node id
 * @param avatarUrl Avatar url
 * @param gravatarId Gravatar id
 * @param url Url
 * @param htmlUrl Html url
 * @param followersUrl Followers url
 * @param subscriptionsUrl Subscriptions url
 * @param organizationsUrl Organizations url
 * @param reposUrl Repos url
 * @param receivedEventsUrl Received events url
 * @param type Type
 * @param score Score
 * @param followingUrl Following url
 * @param gistsUrl Gists url
 * @param starredUrl Starred url
 * @param eventsUrl Events url
 * @param publicRepos Public repos
 * @param publicGists Public gists
 * @param followers Followers
 * @param following Following
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param name Name
 * @param bio Bio
 * @param email Email
 * @param location Location
 * @param siteAdmin Site admin
 * @param hireable Hireable
 * @param textMatches Text matches
 * @param blog Blog
 * @param company Company
 * @param suspendedAt Suspended at
 * @param userViewType User view type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user-search-result-item")
public record GitHubUserSearchResultItem(
        String login,
        long id,
        String nodeId,
        String avatarUrl,
        @Nullable String gravatarId,
        String url,
        String htmlUrl,
        String followersUrl,
        String subscriptionsUrl,
        String organizationsUrl,
        String reposUrl,
        String receivedEventsUrl,
        GitHubUserType type,
        double score,
        String followingUrl,
        String gistsUrl,
        String starredUrl,
        String eventsUrl,
        @Nullable Integer publicRepos,
        @Nullable Integer publicGists,
        @Nullable Integer followers,
        @Nullable Integer following,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable String name,
        @Nullable String bio,
        @Nullable String email,
        @Nullable String location,
        boolean siteAdmin,
        @Nullable Boolean hireable,
        @Nullable List<GitHubSearchTextMatchItem> textMatches,
        @Nullable String blog,
        @Nullable String company,
        @Nullable Instant suspendedAt,
        @Nullable GitHubUserViewType userViewType) {

    public GitHubUserSearchResultItem {
        requireNonNull(login, "login");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(avatarUrl, "avatarUrl");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(followersUrl, "followersUrl");
        requireNonNull(subscriptionsUrl, "subscriptionsUrl");
        requireNonNull(organizationsUrl, "organizationsUrl");
        requireNonNull(reposUrl, "reposUrl");
        requireNonNull(receivedEventsUrl, "receivedEventsUrl");
        requireNonNull(type, "type");
        requireNonNull(followingUrl, "followingUrl");
        requireNonNull(gistsUrl, "gistsUrl");
        requireNonNull(starredUrl, "starredUrl");
        requireNonNull(eventsUrl, "eventsUrl");
    }
}
