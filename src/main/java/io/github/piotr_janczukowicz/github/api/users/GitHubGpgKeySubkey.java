package io.github.piotr_janczukowicz.github.api.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Gpg Key Subkey.
 *
 * @param id Id
 * @param primaryKeyId Primary key id
 * @param keyId Key id
 * @param publicKey Public key
 * @param emails Emails
 * @param subkeys Subkeys
 * @param canSign Can sign
 * @param canEncryptComms Can encrypt comms
 * @param canEncryptStorage Can encrypt storage
 * @param canCertify Can certify
 * @param createdAt Created at
 * @param expiresAt Expires at
 * @param rawKey Raw key
 * @param revoked Revoked
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gpg-key/properties/subkeys/items")
@RecordBuilder
public record GitHubGpgKeySubkey(
        @Nullable Long id,
        @Nullable Long primaryKeyId,
        @Nullable String keyId,
        @Nullable String publicKey,
        @Nullable List<GitHubGpgKeyEmail> emails,

        @SuppressSchemaTypeMismatch("dump items are empty object; java is Map") @Nullable List<Map<String, Object>> subkeys,

        @Nullable Boolean canSign,
        @Nullable Boolean canEncryptComms,
        @Nullable Boolean canEncryptStorage,
        @Nullable Boolean canCertify,
        @Nullable String createdAt,
        @Nullable String expiresAt,
        @Nullable String rawKey,
        @Nullable Boolean revoked) {}
