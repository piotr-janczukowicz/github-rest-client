package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Ldap Private User
 *
 * @param ldapDn ldap dn
 * @param login login
 * @param id id
 * @param nodeId node id
 * @param avatarUrl avatar url
 * @param gravatarId gravatar id
 * @param url url
 * @param htmlUrl html url
 * @param followersUrl followers url
 * @param followingUrl following url
 * @param gistsUrl gists url
 * @param starredUrl starred url
 * @param subscriptionsUrl subscriptions url
 * @param organizationsUrl organizations url
 * @param reposUrl repos url
 * @param eventsUrl events url
 * @param receivedEventsUrl received events url
 * @param type type
 * @param siteAdmin site admin
 * @param name name
 * @param company company
 * @param blog blog
 * @param location location
 * @param email email
 * @param hireable hireable
 * @param bio bio
 * @param twitterUsername twitter username
 * @param publicRepos public repos
 * @param publicGists public gists
 * @param followers followers
 * @param following following
 * @param createdAt created at
 * @param updatedAt updated at
 * @param privateGists private gists
 * @param totalPrivateRepos total private repos
 * @param ownedPrivateRepos owned private repos
 * @param diskUsage disk usage
 * @param collaborators collaborators
 * @param twoFactorAuthentication two factor authentication
 * @param plan plan
 * @param suspendedAt suspended at
 * @param businessPlus business plus
 * @param userViewType user view type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ldap-mapping-user")
@Flavors(GitHubFlavor.GHES)
public record GitHubLdapMappingUser(
        @Nullable String ldapDn,
        String login,
        long id,
        String nodeId,
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
        @Nullable Boolean hireable,
        @Nullable String bio,
        @Nullable String twitterUsername,
        int publicRepos,
        int publicGists,
        int followers,
        int following,
        Instant createdAt,
        Instant updatedAt,
        int privateGists,
        int totalPrivateRepos,
        int ownedPrivateRepos,
        int diskUsage,
        int collaborators,
        boolean twoFactorAuthentication,
        @Nullable GitHubLdapMappingUserPlan plan,
        @Nullable Instant suspendedAt,
        @Nullable Boolean businessPlus,
        @Nullable String userViewType) {
    public GitHubLdapMappingUser {
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
