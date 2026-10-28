package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Attach an enterprise configuration to repositories.
 *
 * @param scope The type of repositories to attach the configuration to.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/code-security/attach-enterprise-configuration/requestBody")
@RecordBuilder
public record GitHubAttachEnterpriseConfigurationPayload(GitHubAttachEnterpriseConfigurationScope scope) {

    public GitHubAttachEnterpriseConfigurationPayload {
        requireNonNull(scope, "scope");
    }
}
