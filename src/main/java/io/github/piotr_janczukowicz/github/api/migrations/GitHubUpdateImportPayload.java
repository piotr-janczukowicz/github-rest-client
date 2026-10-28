package io.github.piotr_janczukowicz.github.api.migrations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update source import request body. All fields are optional; a {@code null} payload restarts the import.
 *
 * @param vcsUsername The username to provide to the originating repository.
 * @param vcsPassword The password to provide to the originating repository.
 * @param vcs The type of version control system you are migrating from.
 * @param tfvcProject For a tfvc import, the name of the project that is being imported.
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/migrations/update-import/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateImportPayload(
        @Nullable String vcsUsername,
        @Nullable String vcsPassword,
        @Nullable GitHubImportVcs vcs,
        @Nullable String tfvcProject) {

    /**
     * Describes this GitHubUpdateImportPayload without credential material.
     *
     * @return text with {@code vcsPassword} redacted
     */
    @Override
    public String toString() {
        return "GitHubUpdateImportPayload[vcsUsername=" + vcsUsername + ", vcsPassword=<redacted>, vcs=" + vcs
                + ", tfvcProject=" + tfvcProject + "]";
    }
}
