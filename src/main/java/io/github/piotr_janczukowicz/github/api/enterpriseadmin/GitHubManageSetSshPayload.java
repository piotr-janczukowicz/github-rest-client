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
 * @param key The public SSH key to add to the `authorized keys` file.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-set-ssh-request")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSetSshPayload(String key) {
    public GitHubManageSetSshPayload {
        requireNonNull(key, "key");
    }
}
