package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A package described by an SPDX document.
 *
 * @param spdxId A unique SPDX identifier for the package.
 * @param name The name of the package.
 * @param versionInfo The version of the package. If the package does not have an exact version specified, a version
 *     range is given.
 * @param downloadLocation The location where the package can be downloaded, or NOASSERTION if this has not been
 *     determined.
 * @param filesAnalyzed Whether the package's file content has been subjected to analysis during the creation of the
 *     SPDX document.
 * @param licenseConcluded The license of the package as determined while creating the SPDX document.
 * @param licenseDeclared The license of the package as declared by its author, or NOASSERTION if this information was
 *     not available when the SPDX document was created.
 * @param supplier The distribution source of this package, or NOASSERTION if this was not determined.
 * @param copyrightText The copyright holders of the package, and any dates present with those notices, if available.
 * @param externalRefs External references of the package.
 * @deprecated Closing down on {@code 2026-11-13} together with the synchronous SBOM export; migrate to the asynchronous
 *     flow (generate a report, then fetch it).
 */
@Deprecated(since = "2026-05-12")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/dependency-graph-spdx-sbom/properties/sbom/properties/packages/items")
@RecordBuilder
public record GitHubSpdxSbomPackage(
        @JsonProperty("SPDXID") String spdxId,
        String name,
        @JsonProperty("versionInfo") String versionInfo,
        @JsonProperty("downloadLocation") String downloadLocation,
        @JsonProperty("filesAnalyzed") boolean filesAnalyzed,
        @JsonProperty("licenseConcluded") @Nullable String licenseConcluded,
        @JsonProperty("licenseDeclared") @Nullable String licenseDeclared,
        @Nullable String supplier,
        @JsonProperty("copyrightText") @Nullable String copyrightText,
        @JsonProperty("externalRefs") @Nullable List<GitHubSpdxSbomExternalRef> externalRefs) {

    public GitHubSpdxSbomPackage {
        requireNonNull(spdxId, "spdxId");
        requireNonNull(name, "name");
        requireNonNull(versionInfo, "versionInfo");
        requireNonNull(downloadLocation, "downloadLocation");
    }
}
