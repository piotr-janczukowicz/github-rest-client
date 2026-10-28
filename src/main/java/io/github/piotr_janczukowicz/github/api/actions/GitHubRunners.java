package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Self-hosted runners of an organization or a repository.
 *
 * @param totalCount Total number of runners
 * @param runners The runners
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/actions/list-self-hosted-runners-for-org/200",
    "operations/actions/list-self-hosted-runners-for-repo/200"
})
public record GitHubRunners(int totalCount, List<GitHubRunner> runners) {

    public GitHubRunners {
        requireNonNull(runners, "runners");
    }
}
