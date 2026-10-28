package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Porter author.
 *
 * @param id Id
 * @param remoteId Remote id
 * @param remoteName Remote name
 * @param email Email
 * @param name Name
 * @param url Url
 * @param importUrl Import url
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/porter-author")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPorterAuthor(
        long id, String remoteId, String remoteName, String email, String name, String url, String importUrl) {

    public GitHubPorterAuthor {
        requireNonNull(remoteId, "remoteId");
        requireNonNull(remoteName, "remoteName");
        requireNonNull(email, "email");
        requireNonNull(name, "name");
        requireNonNull(url, "url");
        requireNonNull(importUrl, "importUrl");
    }
}
