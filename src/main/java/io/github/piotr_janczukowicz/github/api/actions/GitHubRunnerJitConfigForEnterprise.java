package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Just-in-time configuration of a self-hosted runner of an enterprise.
 *
 * @param runner The runner
 * @param encodedJitConfig The base64 encoded runner configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/generate-runner-jitconfig-for-enterprise/201")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubRunnerJitConfigForEnterprise(GitHubRunner runner, String encodedJitConfig) {

    public GitHubRunnerJitConfigForEnterprise {
        requireNonNull(runner, "runner");
        requireNonNull(encodedJitConfig, "encodedJitConfig");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code encodedJitConfig} redacted
     */
    @Override
    public String toString() {
        return "GitHubRunnerJitConfigForEnterprise[runner=" + runner + ", encodedJitConfig=<redacted>]";
    }
}
