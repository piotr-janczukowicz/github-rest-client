package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Discussion author. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/discussion/properties/user")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDiscussionUser(
        @Nullable String avatarUrl,
        @Nullable Boolean deleted,
        @Nullable String email,
        @Nullable String eventsUrl,
        @Nullable String followersUrl,
        @Nullable String followingUrl,
        @Nullable String gistsUrl,
        @Nullable String gravatarId,
        @Nullable String htmlUrl,
        long id,
        String login,
        @Nullable String name,
        @Nullable String nodeId,
        @Nullable String organizationsUrl,
        @Nullable String receivedEventsUrl,
        @Nullable String reposUrl,
        @Nullable Boolean siteAdmin,
        @Nullable String starredUrl,
        @Nullable String subscriptionsUrl,
        @Nullable GitHubDiscussionUserType type,
        @Nullable String url,
        @Nullable String userViewType) {
    public GitHubDiscussionUser {
        requireNonNull(login, "login");
    }
}
