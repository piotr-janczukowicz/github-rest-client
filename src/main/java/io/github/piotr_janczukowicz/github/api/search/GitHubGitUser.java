package io.github.piotr_janczukowicz.github.api.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Metaproperties for Git author/committer information.
 *
 * @param name Name
 * @param email Email
 * @param date Date
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-user")
public record GitHubGitUser(
        @Nullable String name,
        @Nullable String email,
        @Nullable Instant date) {}
