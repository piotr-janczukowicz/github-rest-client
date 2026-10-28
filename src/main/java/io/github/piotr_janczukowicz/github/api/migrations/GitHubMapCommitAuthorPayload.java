package io.github.piotr_janczukowicz.github.api.migrations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Map commit author request body.
 *
 * @param email The new Git author email.
 * @param name The new Git author name.
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/migrations/map-commit-author/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubMapCommitAuthorPayload(
        @Nullable String email, @Nullable String name) {}
