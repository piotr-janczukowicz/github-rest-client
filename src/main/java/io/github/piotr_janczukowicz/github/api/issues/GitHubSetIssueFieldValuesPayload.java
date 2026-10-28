package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Set issue field values for an issue.
 *
 * @param issueFieldValues An array of issue field values to set for this issue. Each field value must include the field
 *     ID and the value to set. All existing field values will be replaced.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/set-issue-field-values/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetIssueFieldValuesPayload(@Nullable List<GitHubSetIssueFieldValue> issueFieldValues) {

    private static final String PARAM_ISSUE_FIELD_VALUES = "issueFieldValues";
    private static final int MAX_ISSUE_FIELD_VALUES = 25;

    public GitHubSetIssueFieldValuesPayload {
        if (issueFieldValues != null) {
            issueFieldValues =
                    List.copyOf(requireSizeAtMost(issueFieldValues, MAX_ISSUE_FIELD_VALUES, PARAM_ISSUE_FIELD_VALUES));
        }
    }
}
