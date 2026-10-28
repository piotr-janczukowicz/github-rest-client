package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GPG Key. A unique encryption key.
 *
 * @param id Id
 * @param name Name
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
 * @param revoked Revoked
 * @param rawKey Raw key
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gpg-key")
@RecordBuilder
public record GitHubGpgKey(
        long id,
        @Nullable String name,
        @Nullable Long primaryKeyId,
        String keyId,
        String publicKey,
        List<GitHubGpgKeyEmail> emails,
        List<GitHubGpgKeySubkey> subkeys,
        boolean canSign,
        boolean canEncryptComms,
        boolean canEncryptStorage,
        boolean canCertify,
        Instant createdAt,
        @Nullable Instant expiresAt,
        boolean revoked,
        @Nullable String rawKey) {

    public GitHubGpgKey {
        requireNonNull(keyId, "keyId");
        requireNonNull(publicKey, "publicKey");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(emails, "emails");
        requireNonNull(subkeys, "subkeys");
    }
}
