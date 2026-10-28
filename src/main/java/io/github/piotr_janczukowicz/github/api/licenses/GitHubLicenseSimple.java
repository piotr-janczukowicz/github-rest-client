package io.github.piotr_janczukowicz.github.api.licenses;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * License Simple.
 *
 * @param key Key
 * @param name Name
 * @param url Url
 * @param spdxId Spdx id
 * @param nodeId Node id
 * @param htmlUrl Html url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/license-simple")
@RecordBuilder
public record GitHubLicenseSimple(
        String key,
        String name,
        @Nullable String url,
        @Nullable String spdxId,
        String nodeId,
        @Nullable String htmlUrl) {

    public GitHubLicenseSimple {
        requireNonNull(key, "key");
        requireNonNull(name, "name");
        requireNonNull(nodeId, "nodeId");
    }
}
