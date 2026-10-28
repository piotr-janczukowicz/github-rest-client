package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Update organization programmatic access grants in bulk.
 *
 * @param action Action to apply to the tokens
 * @param patIds Fine-grained personal access token identifiers
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/update-pat-accesses/requestBody")
public record GitHubUpdatePatAccessesPayload(GitHubOrganizationPatAccessUpdateAction action, List<Long> patIds) {

    private static final String PARAM_PAT_IDS = "patIds";
    private static final int MIN_PAT_IDS = 1;
    private static final int MAX_PAT_IDS = 100;

    public GitHubUpdatePatAccessesPayload {
        requireNonNull(action, "action");
        requireNonNull(patIds, PARAM_PAT_IDS);
        patIds = List.copyOf(requireSizeBetween(patIds, MIN_PAT_IDS, MAX_PAT_IDS, PARAM_PAT_IDS));
    }
}
