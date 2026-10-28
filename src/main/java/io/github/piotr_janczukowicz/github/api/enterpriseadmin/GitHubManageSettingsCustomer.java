package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsCustomer
 *
 * @param name name
 * @param email email
 * @param uuid uuid
 * @param secretKeyData secret key data
 * @param publicKeyData public key data
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/customer")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsCustomer(
        @Nullable String name,
        @Nullable String email,
        @Nullable String uuid,
        @Nullable String secretKeyData,
        @Nullable String publicKeyData) {
    /**
     * Describes this record without its secret.
     *
     * @return text with {@code secretKeyData} redacted
     */
    @Override
    public String toString() {
        return "GitHubManageSettingsCustomer[" + "name=" + name + ", email=" + email + ", uuid=" + uuid
                + ", secretKeyData=<redacted>" + ", publicKeyData=" + publicKeyData + "]";
    }
}
