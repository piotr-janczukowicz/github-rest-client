package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Community profile.
 *
 * @param healthPercentage Health percentage
 * @param description Description
 * @param documentation Documentation
 * @param files Community health files
 * @param updatedAt Updated at
 * @param contentReportsEnabled Content reports enabled
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/community-profile")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCommunityProfile(
        int healthPercentage,
        @Nullable String description,
        @Nullable String documentation,
        GitHubCommunityProfileFiles files,
        @Nullable Instant updatedAt,
        @Nullable Boolean contentReportsEnabled) {

    public GitHubCommunityProfile {
        requireNonNull(files, "files");
    }
}
