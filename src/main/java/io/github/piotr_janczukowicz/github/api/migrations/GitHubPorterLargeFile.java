package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Porter large file.
 *
 * @param refName Ref name
 * @param path Path
 * @param oid Oid
 * @param size Size
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/porter-large-file")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPorterLargeFile(String refName, String path, String oid, int size) {

    public GitHubPorterLargeFile {
        requireNonNull(refName, "refName");
        requireNonNull(path, "path");
        requireNonNull(oid, "oid");
    }
}
