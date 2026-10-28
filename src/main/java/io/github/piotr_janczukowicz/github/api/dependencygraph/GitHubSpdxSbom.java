package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * An SPDX document.
 *
 * @param spdxId The SPDX identifier for the SPDX document.
 * @param spdxVersion The version of the SPDX specification that this document conforms to.
 * @param comment An optional comment about the SPDX document.
 * @param creationInfo Creation information of the SPDX document.
 * @param name The name of the SPDX document.
 * @param dataLicense The license under which the SPDX document is licensed.
 * @param documentNamespace The namespace for the SPDX document.
 * @param packages The packages described by the SPDX document.
 * @param relationships Relationships between SPDX elements.
 * @deprecated Closing down on {@code 2026-11-13} together with the synchronous SBOM export; migrate to the asynchronous
 *     flow (generate a report, then fetch it).
 */
@Deprecated(since = "2026-05-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/dependency-graph-spdx-sbom/properties/sbom")
@RecordBuilder
public record GitHubSpdxSbom(
        @JsonProperty("SPDXID") String spdxId,
        @JsonProperty("spdxVersion") String spdxVersion,
        @Nullable String comment,
        @JsonProperty("creationInfo") GitHubSpdxSbomCreationInfo creationInfo,
        String name,
        @JsonProperty("dataLicense") String dataLicense,
        @JsonProperty("documentNamespace") String documentNamespace,
        List<GitHubSpdxSbomPackage> packages,
        @Nullable List<GitHubSpdxSbomRelationship> relationships) {

    public GitHubSpdxSbom {
        requireNonNull(spdxId, "spdxId");
        requireNonNull(spdxVersion, "spdxVersion");
        requireNonNull(creationInfo, "creationInfo");
        requireNonNull(name, "name");
        requireNonNull(dataLicense, "dataLicense");
        requireNonNull(documentNamespace, "documentNamespace");
        requireNonNull(packages, "packages");
    }
}
