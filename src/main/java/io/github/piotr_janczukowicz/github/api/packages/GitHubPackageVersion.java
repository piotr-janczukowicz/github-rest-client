package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A version of a software package.
 *
 * @param id Unique identifier of the package version.
 * @param name The name of the package version.
 * @param url url
 * @param packageHtmlUrl package html url
 * @param htmlUrl html url
 * @param license license
 * @param description description
 * @param createdAt created at
 * @param updatedAt updated at
 * @param deletedAt deleted at
 * @param metadata metadata
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/package-version")
public record GitHubPackageVersion(
        long id,
        String name,
        String url,
        String packageHtmlUrl,
        @Nullable String htmlUrl,
        @Nullable String license,
        @Nullable String description,
        Instant createdAt,
        Instant updatedAt,
        @Nullable Instant deletedAt,
        @Nullable GitHubPackageVersionMetadata metadata) {

    public GitHubPackageVersion {
        requireNonNull(name, "name");
        requireNonNull(url, "url");
        requireNonNull(packageHtmlUrl, "packageHtmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
