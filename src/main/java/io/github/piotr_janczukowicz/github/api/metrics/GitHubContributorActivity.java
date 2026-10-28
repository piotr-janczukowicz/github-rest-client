package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Contributor activity.
 *
 * @param author Author
 * @param total Total
 * @param weeks Weeks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/contributor-activity")
public record GitHubContributorActivity(
        @Nullable GitHubSimpleUser author, long total, List<GitHubContributorActivityWeek> weeks) {

    public GitHubContributorActivity {
        requireNonNull(weeks, "weeks");
    }
}
