package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsLdap
 *
 * @param host host
 * @param port port
 * @param base base
 * @param uid uid
 * @param bindDn bind dn
 * @param password password
 * @param method method
 * @param searchStrategy search strategy
 * @param userGroups user groups
 * @param adminGroup admin group
 * @param virtualAttributeEnabled virtual attribute enabled
 * @param recursiveGroupSearch recursive group search
 * @param posixSupport posix support
 * @param userSyncEmails user sync emails
 * @param userSyncKeys user sync keys
 * @param userSyncInterval user sync interval
 * @param teamSyncInterval team sync interval
 * @param syncEnabled sync enabled
 * @param reconciliation reconciliation
 * @param profile profile
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/ldap")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsLdap(
        @Nullable String host,
        @Nullable Integer port,
        @Nullable List<String> base,
        @Nullable String uid,
        @Nullable String bindDn,
        @Nullable String password,
        @Nullable String method,
        @Nullable String searchStrategy,
        @Nullable List<String> userGroups,
        @Nullable String adminGroup,
        @Nullable Boolean virtualAttributeEnabled,
        @Nullable Boolean recursiveGroupSearch,
        @Nullable Boolean posixSupport,
        @Nullable Boolean userSyncEmails,
        @Nullable Boolean userSyncKeys,
        @Nullable Integer userSyncInterval,
        @Nullable Integer teamSyncInterval,
        @Nullable Boolean syncEnabled,
        @Nullable GitHubManageSettingsLdapReconciliation reconciliation,
        @Nullable GitHubManageSettingsLdapProfile profile) {
    /**
     * Describes this record without its secret.
     *
     * @return text with {@code password} redacted
     */
    @Override
    public String toString() {
        return "GitHubManageSettingsLdap[" + "host=" + host + ", port=" + port + ", base=" + base + ", uid=" + uid
                + ", bindDn=" + bindDn + ", password=<redacted>" + ", method=" + method + ", searchStrategy="
                + searchStrategy + ", userGroups=" + userGroups + ", adminGroup=" + adminGroup
                + ", virtualAttributeEnabled=" + virtualAttributeEnabled + ", recursiveGroupSearch="
                + recursiveGroupSearch + ", posixSupport=" + posixSupport + ", userSyncEmails=" + userSyncEmails
                + ", userSyncKeys=" + userSyncKeys + ", userSyncInterval=" + userSyncInterval + ", teamSyncInterval="
                + teamSyncInterval + ", syncEnabled=" + syncEnabled + ", reconciliation=" + reconciliation
                + ", profile=" + profile + "]";
    }
}
