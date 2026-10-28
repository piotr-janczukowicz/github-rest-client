package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * An enterprise on GitHub.
 *
 * @param description A short description of the enterprise
 * @param htmlUrl Html url
 * @param websiteUrl The enterprise's website URL
 * @param id Unique identifier of the enterprise
 * @param nodeId Node id
 * @param name The name of the enterprise
 * @param slug The slug url identifier for the enterprise
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param avatarUrl Avatar url
 */
@RecordBuilder
@CatalogSchema("components/schemas/enterprise")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubEnterprise(
        @Nullable String description,
        String htmlUrl,
        @Nullable String websiteUrl,
        long id,
        String nodeId,
        String name,
        String slug,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        String avatarUrl) {

    public GitHubEnterprise {
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(slug, "slug");
        requireNonNull(avatarUrl, "avatarUrl");
    }
}
