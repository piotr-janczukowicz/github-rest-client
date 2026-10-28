package io.github.piotr_janczukowicz.github.api.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Trust domain and services used to verify artifact attestations.
 *
 * @param trustDomain trust domain of the attestation service, or {@code null}
 * @param services hosts of the attestation services, or {@code null}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-overview/properties/domains/properties/artifact_attestations")
public record GitHubApiOverviewArtifactAttestations(
        @Nullable String trustDomain, @Nullable List<String> services) {}
