package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Stargazer. A user who starred a repository.
 *
 * @param starredAt The time the user starred the repository
 * @param user The user who starred the repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/stargazer")
@RecordBuilder
public record GitHubStargazer(Instant starredAt, @Nullable GitHubSimpleUser user) {

    public GitHubStargazer {
        requireNonNull(starredAt, "starredAt");
    }
}
