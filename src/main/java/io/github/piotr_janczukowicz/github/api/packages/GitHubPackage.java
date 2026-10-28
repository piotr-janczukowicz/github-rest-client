package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A software package.
 *
 * @param id Unique identifier of the package.
 * @param name The name of the package.
 * @param packageType package type
 * @param visibility visibility
 * @param url url
 * @param htmlUrl html url
 * @param versionCount The number of versions of the package.
 * @param owner owner
 * @param repository repository
 * @param createdAt created at
 * @param updatedAt updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/package")
public record GitHubPackage(
        long id,
        String name,
        GitHubPackageType packageType,
        GitHubPackageVisibility visibility,
        String url,
        String htmlUrl,
        int versionCount,
        @Nullable GitHubSimpleUser owner,
        @Nullable GitHubMinimalRepository repository,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubPackage {
        requireNonNull(name, "name");
        requireNonNull(packageType, "packageType");
        requireNonNull(visibility, "visibility");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
