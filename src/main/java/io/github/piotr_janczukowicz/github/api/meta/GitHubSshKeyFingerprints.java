package io.github.piotr_janczukowicz.github.api.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * SHA-256 fingerprints of the SSH host keys of github.com, one per key type; a {@code null} fingerprint was not sent.
 *
 * @param sha256Rsa fingerprint of the RSA host key
 * @param sha256Dsa fingerprint of the DSA host key
 * @param sha256Ecdsa fingerprint of the ECDSA host key
 * @param sha256Ed25519 fingerprint of the Ed25519 host key
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-overview/properties/ssh_key_fingerprints")
public record GitHubSshKeyFingerprints(
        @JsonProperty("SHA256_RSA") @Nullable String sha256Rsa,
        @JsonProperty("SHA256_DSA") @Nullable String sha256Dsa,
        @JsonProperty("SHA256_ECDSA") @Nullable String sha256Ecdsa,
        @JsonProperty("SHA256_ED25519") @Nullable String sha256Ed25519) {}
