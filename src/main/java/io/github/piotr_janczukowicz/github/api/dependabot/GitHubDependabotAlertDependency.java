package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Details for the vulnerable dependency.
 *
 * @param dependencyPackage Details for the vulnerable package.
 * @param manifestPath The full path to the dependency manifest file, relative to the root of the repository.
 * @param versionRequirement The dependency version or version constraint last recorded for the dependency associated
 *     with this alert.
 * @param scope The execution scope of the vulnerable dependency.
 * @param relationship The vulnerable dependency's relationship to your project.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/dependabot-alert/properties/dependency",
    "components/schemas/dependabot-alert-with-repository/properties/dependency"
})
public record GitHubDependabotAlertDependency(
        @JsonProperty("package") @Nullable GitHubDependabotAlertPackage dependencyPackage,
        @Nullable String manifestPath,
        @Nullable String versionRequirement,
        @Nullable GitHubDependabotAlertDependencyScope scope,
        @Nullable GitHubDependabotAlertDependencyRelationship relationship) {}
