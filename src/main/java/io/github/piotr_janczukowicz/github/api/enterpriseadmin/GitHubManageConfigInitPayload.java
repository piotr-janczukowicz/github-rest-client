package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Initialize configuration with license upload
 *
 * @param license The content of your .ghl license file.
 * @param password The root site administrator password.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-config-init-request")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageConfigInitPayload(String license, String password) {
    public GitHubManageConfigInitPayload {
        requireNonNull(license, "license");
        requireNonNull(password, "password");
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code password} redacted
     */
    @Override
    public String toString() {
        return "GitHubManageConfigInitPayload[" + "license=" + license + ", password=<redacted>" + "]";
    }
}
