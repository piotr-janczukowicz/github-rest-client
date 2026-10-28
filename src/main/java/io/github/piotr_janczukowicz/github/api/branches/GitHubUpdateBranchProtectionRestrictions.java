package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Push restrictions for branch protection updates.
 *
 * @param users User logins with push access
 * @param teams Team slugs with push access
 * @param apps App slugs with push access
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-branch-protection/requestBody/properties/restrictions")
public record GitHubUpdateBranchProtectionRestrictions(
        List<String> users, List<String> teams, @Nullable List<String> apps) {

    public GitHubUpdateBranchProtectionRestrictions {
        users = List.copyOf(requireNonNull(users, "users"));
        teams = List.copyOf(requireNonNull(teams, "teams"));
        if (apps != null) {
            apps = List.copyOf(apps);
        }
    }
}
