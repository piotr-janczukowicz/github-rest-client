package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Status check policy check.
 *
 * @param context Context
 * @param appId App id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/status-check-policy/properties/checks/items",
    "components/schemas/protected-branch-required-status-check/properties/checks/items"
})
public record GitHubStatusCheckPolicyCheck(String context, OptionalNull<Long> appId) {

    public GitHubStatusCheckPolicyCheck {
        requireNonNull(context, "context");
        requireNonNull(appId, "appId");
    }
}
