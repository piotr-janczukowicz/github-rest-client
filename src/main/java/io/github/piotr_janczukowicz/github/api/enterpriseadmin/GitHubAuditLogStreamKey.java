package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Public key that GitHub uses to sign audit log stream payloads.
 *
 * @param keyId The identifier of the key
 * @param key The public key
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/audit-log-stream-key")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAuditLogStreamKey(String keyId, String key) {

    public GitHubAuditLogStreamKey {
        requireNonNull(keyId, "keyId");
        requireNonNull(key, "key");
    }
}
