package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * App access restrictions request body.
 *
 * @param apps GitHub App slugs with push access
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/set-app-access-restrictions/requestBody",
    "operations/repos/add-app-access-restrictions/requestBody",
    "operations/repos/remove-app-access-restrictions/requestBody"
})
public record GitHubAppAccessRestrictionsPayload(List<String> apps) {

    private static final String PARAM_APPS = "apps";

    public GitHubAppAccessRestrictionsPayload {
        apps = List.copyOf(requireNonNull(apps, PARAM_APPS));
    }
}
