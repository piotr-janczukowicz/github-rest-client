package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Set SSH key
 *
 * @param key The public SSH key to remove from the `authorized keys` file.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-delete-ssh-request")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageDeleteSshPayload(String key) {
    public GitHubManageDeleteSshPayload {
        requireNonNull(key, "key");
    }
}
