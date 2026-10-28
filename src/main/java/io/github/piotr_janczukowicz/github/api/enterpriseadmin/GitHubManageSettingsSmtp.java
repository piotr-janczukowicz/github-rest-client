package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsSmtp
 *
 * @param enabled enabled
 * @param address address
 * @param authentication authentication
 * @param port port
 * @param domain domain
 * @param username username
 * @param userName user name
 * @param enableStarttlsAuto enable starttls auto
 * @param password password
 * @param discardToNoreplyAddress discard-to-noreply-address
 * @param supportAddress support address
 * @param supportAddressType support address type
 * @param noreplyAddress noreply address
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/smtp")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsSmtp(
        @Nullable Boolean enabled,
        @Nullable String address,
        @Nullable String authentication,
        @Nullable String port,
        @Nullable String domain,
        @Nullable String username,
        @Nullable String userName,
        @Nullable Boolean enableStarttlsAuto,
        @Nullable String password,

        @Nullable @JsonProperty("discard-to-noreply-address")
        Boolean discardToNoreplyAddress,

        @Nullable String supportAddress,
        @Nullable String supportAddressType,
        @Nullable String noreplyAddress) {
    /**
     * Describes this record without its secret.
     *
     * @return text with {@code password} redacted
     */
    @Override
    public String toString() {
        return "GitHubManageSettingsSmtp[" + "enabled=" + enabled + ", address=" + address + ", authentication="
                + authentication + ", port=" + port + ", domain=" + domain + ", username=" + username + ", userName="
                + userName + ", enableStarttlsAuto=" + enableStarttlsAuto + ", password=<redacted>"
                + ", discardToNoreplyAddress=" + discardToNoreplyAddress + ", supportAddress=" + supportAddress
                + ", supportAddressType=" + supportAddressType + ", noreplyAddress=" + noreplyAddress + "]";
    }
}
