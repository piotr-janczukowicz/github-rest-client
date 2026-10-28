package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHubCreatePreReceiveEnvironmentPayload
 *
 * @param name The new pre-receive environment's name.
 * @param imageUrl URL from which to download a tarball of this environment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/create-pre-receive-environment/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubCreatePreReceiveEnvironmentPayload(String name, String imageUrl) {
    public GitHubCreatePreReceiveEnvironmentPayload {
        requireNonNull(name, "name");
        requireNonNull(imageUrl, "imageUrl");
    }
}
