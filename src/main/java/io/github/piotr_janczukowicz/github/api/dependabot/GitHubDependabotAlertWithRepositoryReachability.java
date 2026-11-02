package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The reachability result for the vulnerability that triggered the alert, indicating whether a vulnerable function is
 * reachable from the repository's own code.
 *
 * @param verdict The reachability verdict for the alert.
 *     <ul>
 *       <li>{@code reachable}: a path from the repository's code reaches a vulnerable function.
 *       <li>{@code unreachable}: no path from the repository's code reaches a vulnerable function.
 *       <li>{@code potentially_reachable}: reachability could not be determined with confidence; review manually.
 *       <li>{@code unavailable}: no verdict has been computed for the alert yet.
 *       <li>{@code disabled}: reachability analysis is not enabled for the repository.
 *     </ul>
 *
 * @param reason The reason for the reachability verdict. This field is omitted when reachability analysis is disabled
 *     or no result has been computed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-with-repository/properties/reachability")
public record GitHubDependabotAlertWithRepositoryReachability(
        GitHubDependabotAlertWithRepositoryReachabilityVerdict verdict,
        @Nullable GitHubDependabotAlertWithRepositoryReachabilityReason reason) {
    public GitHubDependabotAlertWithRepositoryReachability {
        requireNonNull(verdict, "verdict");
    }
}
