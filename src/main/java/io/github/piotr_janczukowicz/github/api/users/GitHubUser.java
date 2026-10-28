package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Public User.
 *
 * @param login Login
 * @param id Id
 * @param nodeId Node id
 * @param userViewType User view type
 * @param avatarUrl Avatar url
 * @param gravatarId Gravatar id
 * @param url Url
 * @param htmlUrl Html url
 * @param followersUrl Followers url
 * @param followingUrl Following url
 * @param gistsUrl Gists url
 * @param starredUrl Starred url
 * @param subscriptionsUrl Subscriptions url
 * @param organizationsUrl Organizations url
 * @param reposUrl Repos url
 * @param eventsUrl Events url
 * @param receivedEventsUrl Received events url
 * @param type Type
 * @param siteAdmin Site admin
 * @param name Name
 * @param company Company
 * @param blog Blog
 * @param location Location
 * @param email Email
 * @param notificationEmail Notification email
 * @param hireable Hireable
 * @param bio Bio
 * @param twitterUsername Twitter username
 * @param publicRepos Public repos
 * @param publicGists Public gists
 * @param followers Followers
 * @param following Following
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param plan Plan
 * @param privateGists Private gists
 * @param totalPrivateRepos Total private repos
 * @param ownedPrivateRepos Owned private repos
 * @param diskUsage Disk usage
 * @param collaborators Collaborators
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/public-user")
@RecordBuilder
public record GitHubUser(
        String login,
        long id,
        String nodeId,
        @Nullable GitHubUserViewType userViewType,
        String avatarUrl,
        @Nullable String gravatarId,
        String url,
        String htmlUrl,
        String followersUrl,
        String followingUrl,
        String gistsUrl,
        String starredUrl,
        String subscriptionsUrl,
        String organizationsUrl,
        String reposUrl,
        String eventsUrl,
        String receivedEventsUrl,
        String type,
        boolean siteAdmin,
        @Nullable String name,
        @Nullable String company,
        @Nullable String blog,
        @Nullable String location,
        @Nullable String email,
        @Nullable String notificationEmail,
        @Nullable Boolean hireable,
        @Nullable String bio,
        @Nullable String twitterUsername,
        int publicRepos,
        int publicGists,
        int followers,
        int following,
        Instant createdAt,
        Instant updatedAt,
        @Nullable GitHubPlan plan,
        @Nullable Integer privateGists,
        @Nullable Integer totalPrivateRepos,
        @Nullable Integer ownedPrivateRepos,
        @Nullable Integer diskUsage,
        @Nullable Integer collaborators)
        implements GitHubUserView {

    public GitHubUser {
        requireNonNull(login, "login");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(avatarUrl, "avatarUrl");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(followersUrl, "followersUrl");
        requireNonNull(followingUrl, "followingUrl");
        requireNonNull(gistsUrl, "gistsUrl");
        requireNonNull(starredUrl, "starredUrl");
        requireNonNull(subscriptionsUrl, "subscriptionsUrl");
        requireNonNull(organizationsUrl, "organizationsUrl");
        requireNonNull(reposUrl, "reposUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(receivedEventsUrl, "receivedEventsUrl");
        requireNonNull(type, "type");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
