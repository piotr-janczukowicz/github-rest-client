package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * End-of-life schedule of a self-hosted runner version of an enterprise.
 *
 * @param runnerVersion The runner version string.
 * @param registrationDeprecatesAt The date after which this runner version can no longer register. Null if no schedule
 *     is set.
 * @param runtimeDeprecatesAt The date after which jobs will no longer be dispatched to runners on this version.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-runner-version-deprecation-for-enterprise/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubRunnerVersionDeprecationForEnterprise(
        String runnerVersion,
        @Nullable Instant registrationDeprecatesAt,
        @Nullable Instant runtimeDeprecatesAt) {

    public GitHubRunnerVersionDeprecationForEnterprise {
        requireNonNull(runnerVersion, "runnerVersion");
    }
}
