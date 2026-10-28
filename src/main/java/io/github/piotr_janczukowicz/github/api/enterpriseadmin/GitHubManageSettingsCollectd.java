package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsCollectd
 *
 * @param enabled enabled
 * @param server server
 * @param port port
 * @param encryption encryption
 * @param username username
 * @param password password
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/collectd")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsCollectd(
        @Nullable Boolean enabled,
        @Nullable String server,
        @Nullable Integer port,
        @Nullable String encryption,
        @Nullable String username,
        @Nullable String password) {
    /**
     * Describes this record without its secret.
     *
     * @return text with {@code password} redacted
     */
    @Override
    public String toString() {
        return "GitHubManageSettingsCollectd[" + "enabled=" + enabled + ", server=" + server + ", port=" + port
                + ", encryption=" + encryption + ", username=" + username + ", password=<redacted>" + "]";
    }
}
