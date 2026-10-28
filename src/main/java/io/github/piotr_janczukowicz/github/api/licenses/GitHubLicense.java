package io.github.piotr_janczukowicz.github.api.licenses;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * License.
 *
 * @param key Key
 * @param name Name
 * @param url Url
 * @param spdxId Spdx id
 * @param nodeId Node id
 * @param htmlUrl Html url
 * @param description Description
 * @param implementation Implementation
 * @param permissions Permissions
 * @param conditions Conditions
 * @param limitations Limitations
 * @param body Body
 * @param featured Featured
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/license")
@RecordBuilder
public record GitHubLicense(
        String key,
        String name,
        @Nullable String url,
        @Nullable String spdxId,
        String nodeId,
        String htmlUrl,
        String description,
        String implementation,
        List<String> permissions,
        List<String> conditions,
        List<String> limitations,
        String body,
        boolean featured) {

    public GitHubLicense {
        requireNonNull(key, "key");
        requireNonNull(name, "name");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(description, "description");
        requireNonNull(implementation, "implementation");
        requireNonNull(body, "body");
        requireNonNull(permissions, "permissions");
        requireNonNull(conditions, "conditions");
        requireNonNull(limitations, "limitations");
    }
}
