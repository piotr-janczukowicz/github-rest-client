package io.github.piotr_janczukowicz.github.api.projects;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/** Add project field request body for a user-owned project. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/projects/add-field-for-user/requestBody")
public sealed interface GitHubAddFieldForUserPayload
        permits GitHubAddProjectFieldPayload.Basic,
                GitHubAddProjectFieldPayload.SingleSelect,
                GitHubAddProjectFieldPayload.Iteration {

    /**
     * Adds a text, number, or date field.
     *
     * @param name field name
     * @param dataType field data type
     * @return payload
     */
    static GitHubAddProjectFieldPayload.Basic basic(final String name, final GitHubProjectFieldDataType dataType) {
        return GitHubAddProjectFieldPayload.basic(name, dataType);
    }

    /**
     * Adds a single-select field.
     *
     * @param name field name
     * @param singleSelectOptions select options
     * @return payload
     */
    static GitHubAddProjectFieldPayload.SingleSelect singleSelect(
            final String name, final List<GitHubProjectFieldSingleSelectOption> singleSelectOptions) {
        return GitHubAddProjectFieldPayload.singleSelect(name, singleSelectOptions);
    }

    /**
     * Adds an iteration field.
     *
     * @param name field name
     * @param iterationConfiguration iteration configuration
     * @return payload
     */
    static GitHubAddProjectFieldPayload.Iteration iteration(
            final String name, final GitHubProjectFieldIterationConfiguration iterationConfiguration) {
        return GitHubAddProjectFieldPayload.iteration(name, iterationConfiguration);
    }
}
