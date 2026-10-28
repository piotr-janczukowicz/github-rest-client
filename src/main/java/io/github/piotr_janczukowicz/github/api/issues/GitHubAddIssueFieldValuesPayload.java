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
 * Add issue field values to an issue.
 *
 * @param issueFieldValues An array of issue field values to add to this issue. Each field value must include the field
 *     ID and the value to set.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/add-issue-field-values/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAddIssueFieldValuesPayload(@Nullable List<GitHubCreateIssueFieldValue> issueFieldValues) {

    private static final String PARAM_ISSUE_FIELD_VALUES = "issueFieldValues";
    private static final int MAX_ISSUE_FIELD_VALUES = 25;

    public GitHubAddIssueFieldValuesPayload {
        if (issueFieldValues != null) {
            issueFieldValues =
                    List.copyOf(requireSizeAtMost(issueFieldValues, MAX_ISSUE_FIELD_VALUES, PARAM_ISSUE_FIELD_VALUES));
        }
    }
}
