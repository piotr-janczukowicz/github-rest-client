package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Status Check Policy
 *
 * @param url Url
 * @param strict Strict
 * @param contexts Contexts
 * @param checks Checks
 * @param contextsUrl Contexts url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/status-check-policy")
public record GitHubStatusCheckPolicy(
        String url,
        boolean strict,
        List<String> contexts,
        List<GitHubStatusCheckPolicyCheck> checks,
        String contextsUrl) {

    public GitHubStatusCheckPolicy {
        requireNonNull(url, "url");
        requireNonNull(contexts, "contexts");
        requireNonNull(checks, "checks");
        requireNonNull(contextsUrl, "contextsUrl");
    }
}
