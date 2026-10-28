package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Actor. The user or organization that performed an event.
 *
 * @param id Actor id
 * @param login Login
 * @param displayLogin Display login
 * @param gravatarId Gravatar id
 * @param url Url
 * @param avatarUrl Avatar url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/actor")
@RecordBuilder
public record GitHubActor(
        long id,
        String login,
        @Nullable String displayLogin,
        @Nullable String gravatarId,
        String url,
        String avatarUrl) {

    public GitHubActor {
        requireNonNull(login, "login");
        requireNonNull(url, "url");
        requireNonNull(avatarUrl, "avatarUrl");
    }
}
