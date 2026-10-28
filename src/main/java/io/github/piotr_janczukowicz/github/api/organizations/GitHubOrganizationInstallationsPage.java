package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List app installations for an organization response.
 *
 * @param totalCount Total count
 * @param installations Installations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/orgs/list-app-installations/200")
@RecordBuilder
public record GitHubOrganizationInstallationsPage(int totalCount, List<GitHubInstallation> installations) {

    public GitHubOrganizationInstallationsPage {
        requireNonNull(installations, "installations");
    }
}
