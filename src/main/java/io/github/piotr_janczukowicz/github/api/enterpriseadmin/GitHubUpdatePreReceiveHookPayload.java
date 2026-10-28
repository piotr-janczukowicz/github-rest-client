package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Request to update a pre-receive hook.
 *
 * @param name The name of the hook
 * @param script The script that the hook runs
 * @param scriptRepository The GitHub repository where the script is kept; copied
 * @param environment The pre-receive environment where the script is executed; copied
 * @param enforcement The state of enforcement for this hook
 * @param allowDownstreamConfiguration Whether enforcement can be overridden at the organization or repository level
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-pre-receive-hook/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdatePreReceiveHookPayload(
        @Nullable String name,
        @Nullable String script,
        @Nullable Map<String, Object> scriptRepository,
        @Nullable Map<String, Object> environment,
        @Nullable String enforcement,
        @Nullable Boolean allowDownstreamConfiguration) {
    public GitHubUpdatePreReceiveHookPayload {
        if (scriptRepository != null) {
            scriptRepository = Map.copyOf(scriptRepository);
        }
        if (environment != null) {
            environment = Map.copyOf(environment);
        }
    }
}
