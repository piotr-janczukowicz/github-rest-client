package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Start source import request body.
 *
 * @param vcsUrl The URL of the originating repository.
 * @param vcs The originating VCS type. Without this parameter, the import job will take additional time to detect the
 *     VCS type before beginning the import. This detection step will be reflected in the response.
 * @param vcsUsername If authentication is required, the username to provide to {@code vcs_url}.
 * @param vcsPassword If authentication is required, the password to provide to {@code vcs_url}.
 * @param tfvcProject For a tfvc import, the name of the project that is being imported.
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/migrations/start-import/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubStartImportPayload(
        String vcsUrl,
        @Nullable GitHubImportVcs vcs,
        @Nullable String vcsUsername,
        @Nullable String vcsPassword,
        @Nullable String tfvcProject) {

    public GitHubStartImportPayload {
        requireNonNull(vcsUrl, "vcsUrl");
    }

    /**
     * Describes this GitHubStartImportPayload without credential material.
     *
     * @return text with {@code vcsPassword} redacted
     */
    @Override
    public String toString() {
        return "GitHubStartImportPayload[vcsUrl=" + vcsUrl + ", vcs=" + vcs + ", vcsUsername=" + vcsUsername
                + ", vcsPassword=<redacted>, tfvcProject=" + tfvcProject + "]";
    }
}
