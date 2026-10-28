package io.github.piotr_janczukowicz.github.api.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update Authenticated User.
 *
 * @param name The new name of the user.
 * @param email The publicly visible email address of the user.
 * @param blog The new blog URL of the user.
 * @param twitterUsername The new Twitter username of the user.
 * @param company The new company of the user.
 * @param location The new location of the user.
 * @param hireable The new hiring availability of the user.
 * @param bio The new short biography of the user.
 */
@CatalogSchema("operations/users/update-authenticated/requestBody")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubUpdateAuthenticatedUserPayload(
        @Nullable String name,
        @Nullable String email,
        @Nullable String blog,
        @Nullable OptionalNull<String> twitterUsername,
        @Nullable String company,
        @Nullable String location,
        @Nullable Boolean hireable,
        @Nullable String bio) {}
