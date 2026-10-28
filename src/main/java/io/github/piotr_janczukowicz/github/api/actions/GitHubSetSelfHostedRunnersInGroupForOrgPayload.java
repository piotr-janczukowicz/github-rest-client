package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Self-hosted runners of a runner group.
 *
 * @param runners List of runner IDs to add to the runner group
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-self-hosted-runners-in-group-for-org/requestBody")
public record GitHubSetSelfHostedRunnersInGroupForOrgPayload(List<Long> runners) {

    public GitHubSetSelfHostedRunnersInGroupForOrgPayload {
        runners = List.copyOf(requireNonNull(runners, "runners"));
    }
}
