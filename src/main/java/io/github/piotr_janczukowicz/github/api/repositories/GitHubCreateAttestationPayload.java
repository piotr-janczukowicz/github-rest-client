package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Create repository attestation ({@code repos/create-attestation}).
 *
 * @param bundle Attestation Sigstore bundle
 */
@RecordBuilder
@CatalogSchema("operations/repos/create-attestation/requestBody")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateAttestationPayload(CreateAttestationBundle bundle) {

    public GitHubCreateAttestationPayload {
        requireNonNull(bundle, "bundle");
    }

    /**
     * Attestation Sigstore bundle.
     *
     * @param mediaType Bundle media type
     * @param verificationMaterial Verification material
     * @param dsseEnvelope DSSE envelope
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema("operations/repos/create-attestation/requestBody/properties/bundle")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public record CreateAttestationBundle(
            @JsonProperty("mediaType") @Nullable String mediaType,
            @JsonProperty("verificationMaterial") @Nullable Map<String, Object> verificationMaterial,
            @JsonProperty("dsseEnvelope") @Nullable Map<String, Object> dsseEnvelope) {

        public CreateAttestationBundle {
            if (verificationMaterial != null) {
                verificationMaterial = Collections.unmodifiableMap(new LinkedHashMap<>(verificationMaterial));
            }
            if (dsseEnvelope != null) {
                dsseEnvelope = Collections.unmodifiableMap(new LinkedHashMap<>(dsseEnvelope));
            }
        }
    }
}
