package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A CodeQL database.
 *
 * @param id The ID of the CodeQL database.
 * @param name The name of the CodeQL database.
 * @param language The language of the CodeQL database.
 * @param uploader A GitHub user.
 * @param contentType The MIME type of the CodeQL database file.
 * @param size The size of the CodeQL database file in bytes.
 * @param createdAt The date and time at which the CodeQL database was created, in ISO 8601 format':' YYYY-MM-
 *     DDTHH:MM:SSZ.
 * @param updatedAt The date and time at which the CodeQL database was last updated, in ISO 8601 format':' YYYY-
 *     MM-DDTHH:MM:SSZ.
 * @param url The URL at which to download the CodeQL database.
 * @param commitOid The commit SHA of the repository at the time the CodeQL database was created.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-codeql-database")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningCodeqlDatabase(
        long id,
        String name,
        String language,
        GitHubSimpleUser uploader,
        String contentType,
        int size,
        Instant createdAt,
        Instant updatedAt,
        String url,
        @Nullable String commitOid) {
    public GitHubCodeScanningCodeqlDatabase {
        requireNonNull(name, "name");
        requireNonNull(language, "language");
        requireNonNull(uploader, "uploader");
        requireNonNull(contentType, "contentType");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(url, "url");
    }
}
