package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Plan.
 *
 * @param collaborators Collaborators
 * @param name Name
 * @param space Space
 * @param privateRepos Private repos
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"components/schemas/private-user/properties/plan", "components/schemas/public-user/properties/plan"})
@RecordBuilder
public record GitHubPlan(int collaborators, String name, int space, int privateRepos) {

    public GitHubPlan {
        requireNonNull(name, "name");
    }
}
