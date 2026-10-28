package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Request body for {@code POST /repos/{owner}/{repo}/check-suites}.
 *
 * @param headSha The sha of the head commit.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/create-suite/requestBody")
public record GitHubCreateSuitePayload(String headSha) {

    public GitHubCreateSuitePayload {
        requireNonNull(headSha, "headSha");
    }
}
