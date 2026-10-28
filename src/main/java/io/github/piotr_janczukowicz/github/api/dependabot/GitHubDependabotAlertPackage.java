package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Details for the vulnerable package.
 *
 * @param ecosystem The package's language or package management ecosystem.
 * @param name The unique package name within its ecosystem.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-package")
public record GitHubDependabotAlertPackage(String ecosystem, String name) {

    public GitHubDependabotAlertPackage {
        requireNonNull(ecosystem, "ecosystem");
        requireNonNull(name, "name");
    }
}
