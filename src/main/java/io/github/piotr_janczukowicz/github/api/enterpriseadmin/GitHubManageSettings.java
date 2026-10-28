package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettings
 *
 * @param privateMode private mode
 * @param publicPages public pages
 * @param subdomainIsolation subdomain isolation
 * @param signupEnabled signup enabled
 * @param githubHostname github hostname
 * @param identiconsHost identicons host
 * @param httpProxy http proxy
 * @param authMode auth mode
 * @param expireSessions expire sessions
 * @param adminPassword admin password
 * @param configurationId configuration id
 * @param configurationRunCount configuration run count
 * @param avatar avatar
 * @param customer customer
 * @param license license
 * @param githubSsl github ssl
 * @param ldap ldap
 * @param cas cas
 * @param saml saml
 * @param githubOauth github oauth
 * @param smtp smtp
 * @param ntp ntp
 * @param timezone timezone
 * @param snmp snmp
 * @param syslog syslog
 * @param assets assets
 * @param pages pages
 * @param collectd collectd
 * @param mapping mapping
 * @param loadBalancer load balancer
 * @param prometheus prometheus
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettings(
        @Nullable Boolean privateMode,
        @Nullable Boolean publicPages,
        @Nullable Boolean subdomainIsolation,
        @Nullable Boolean signupEnabled,
        @Nullable String githubHostname,
        @Nullable String identiconsHost,
        @Nullable String httpProxy,
        @Nullable String authMode,
        @Nullable Boolean expireSessions,
        @Nullable String adminPassword,
        @Nullable Integer configurationId,
        @Nullable Integer configurationRunCount,
        @Nullable GitHubManageSettingsAvatar avatar,
        @Nullable GitHubManageSettingsCustomer customer,
        @Nullable GitHubManageSettingsLicense license,
        @Nullable GitHubManageSettingsGithubSsl githubSsl,
        @Nullable GitHubManageSettingsLdap ldap,
        @Nullable GitHubManageSettingsCas cas,
        @Nullable GitHubManageSettingsSaml saml,
        @Nullable GitHubManageSettingsGithubOauth githubOauth,
        @Nullable GitHubManageSettingsSmtp smtp,
        @Nullable GitHubManageSettingsNtp ntp,
        @Nullable String timezone,
        @Nullable GitHubManageSettingsSnmp snmp,
        @Nullable GitHubManageSettingsSyslog syslog,
        @Nullable String assets,
        @Nullable GitHubManageSettingsPages pages,
        @Nullable GitHubManageSettingsCollectd collectd,
        @Nullable GitHubManageSettingsMapping mapping,
        @Nullable String loadBalancer,
        @Nullable GitHubManageSettingsPrometheus prometheus) {
    /**
     * Describes this record without its secret.
     *
     * @return text with {@code adminPassword} redacted
     */
    @Override
    public String toString() {
        return "GitHubManageSettings[" + "privateMode=" + privateMode + ", publicPages=" + publicPages
                + ", subdomainIsolation=" + subdomainIsolation + ", signupEnabled=" + signupEnabled
                + ", githubHostname=" + githubHostname + ", identiconsHost=" + identiconsHost + ", httpProxy="
                + httpProxy + ", authMode=" + authMode + ", expireSessions=" + expireSessions
                + ", adminPassword=<redacted>" + ", configurationId=" + configurationId + ", configurationRunCount="
                + configurationRunCount + ", avatar=" + avatar + ", customer=" + customer + ", license=" + license
                + ", githubSsl=" + githubSsl + ", ldap=" + ldap + ", cas=" + cas + ", saml=" + saml + ", githubOauth="
                + githubOauth + ", smtp=" + smtp + ", ntp=" + ntp + ", timezone=" + timezone + ", snmp=" + snmp
                + ", syslog=" + syslog + ", assets=" + assets + ", pages=" + pages + ", collectd=" + collectd
                + ", mapping=" + mapping + ", loadBalancer=" + loadBalancer + ", prometheus=" + prometheus + "]";
    }
}
