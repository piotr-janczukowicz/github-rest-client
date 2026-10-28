package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Feature options for Automatic dependency submission.
 *
 * @param labeledRunners Whether to use runners labeled with 'dependency-submission' or standard GitHub runners.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/code-security-configuration/properties/dependency_graph_autosubmit_action_options",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/dependency_graph_autosubmit_action_options",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/dependency_graph_autosubmit_action_options",
    "operations/code-security/create-configuration/requestBody/properties/dependency_graph_autosubmit_action_options",
    "operations/code-security/update-configuration/requestBody/properties/dependency_graph_autosubmit_action_options"
})
@RecordBuilder
public record GitHubDependencyGraphAutosubmitActionOptions(
        @Nullable Boolean labeledRunners) {}
