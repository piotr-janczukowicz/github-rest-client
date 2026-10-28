package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Enablement status of a code security feature. */
@CatalogSchema({
    "components/schemas/code-security-configuration/properties/code_scanning_default_setup",
    "components/schemas/code-security-configuration/properties/code_scanning_delegated_alert_dismissal",
    "components/schemas/code-security-configuration/properties/dependabot_alerts",
    "components/schemas/code-security-configuration/properties/dependabot_delegated_alert_dismissal",
    "components/schemas/code-security-configuration/properties/dependabot_security_updates",
    "components/schemas/code-security-configuration/properties/dependency_graph",
    "components/schemas/code-security-configuration/properties/dependency_graph_autosubmit_action",
    "components/schemas/code-security-configuration/properties/private_vulnerability_reporting",
    "components/schemas/code-security-configuration/properties/secret_scanning",
    "components/schemas/code-security-configuration/properties/secret_scanning_delegated_alert_dismissal",
    "components/schemas/code-security-configuration/properties/secret_scanning_delegated_bypass",
    "components/schemas/code-security-configuration/properties/secret_scanning_extended_metadata",
    "components/schemas/code-security-configuration/properties/secret_scanning_generic_secrets",
    "components/schemas/code-security-configuration/properties/secret_scanning_non_provider_patterns",
    "components/schemas/code-security-configuration/properties/secret_scanning_push_protection",
    "components/schemas/code-security-configuration/properties/secret_scanning_validity_checks",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/code_scanning_default_setup",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/code_scanning_delegated_alert_dismissal",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/code_security",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/dependabot_alerts",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/dependabot_security_updates",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/dependency_graph",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/dependency_graph_autosubmit_action",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/private_vulnerability_reporting",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/secret_protection",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/secret_scanning",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/secret_scanning_delegated_alert_dismissal",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/secret_scanning_extended_metadata",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/secret_scanning_generic_secrets",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/secret_scanning_non_provider_patterns",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/secret_scanning_push_protection",
    "operations/code-security/create-configuration-for-enterprise/requestBody/properties/secret_scanning_validity_checks",
    "operations/code-security/create-configuration/requestBody/properties/code_scanning_default_setup",
    "operations/code-security/create-configuration/requestBody/properties/code_scanning_delegated_alert_dismissal",
    "operations/code-security/create-configuration/requestBody/properties/code_security",
    "operations/code-security/create-configuration/requestBody/properties/dependabot_alerts",
    "operations/code-security/create-configuration/requestBody/properties/dependabot_delegated_alert_dismissal",
    "operations/code-security/create-configuration/requestBody/properties/dependabot_security_updates",
    "operations/code-security/create-configuration/requestBody/properties/dependency_graph",
    "operations/code-security/create-configuration/requestBody/properties/dependency_graph_autosubmit_action",
    "operations/code-security/create-configuration/requestBody/properties/private_vulnerability_reporting",
    "operations/code-security/create-configuration/requestBody/properties/secret_protection",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_delegated_alert_dismissal",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_delegated_bypass",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_extended_metadata",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_generic_secrets",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_non_provider_patterns",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_push_protection",
    "operations/code-security/create-configuration/requestBody/properties/secret_scanning_validity_checks",
    "operations/code-security/update-configuration/requestBody/properties/code_scanning_default_setup",
    "operations/code-security/update-configuration/requestBody/properties/code_scanning_delegated_alert_dismissal",
    "operations/code-security/update-configuration/requestBody/properties/code_security",
    "operations/code-security/update-configuration/requestBody/properties/dependabot_alerts",
    "operations/code-security/update-configuration/requestBody/properties/dependabot_delegated_alert_dismissal",
    "operations/code-security/update-configuration/requestBody/properties/dependabot_security_updates",
    "operations/code-security/update-configuration/requestBody/properties/dependency_graph",
    "operations/code-security/update-configuration/requestBody/properties/dependency_graph_autosubmit_action",
    "operations/code-security/update-configuration/requestBody/properties/private_vulnerability_reporting",
    "operations/code-security/update-configuration/requestBody/properties/secret_protection",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_delegated_alert_dismissal",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_delegated_bypass",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_extended_metadata",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_generic_secrets",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_non_provider_patterns",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_push_protection",
    "operations/code-security/update-configuration/requestBody/properties/secret_scanning_validity_checks",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/code_scanning_default_setup",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/code_scanning_delegated_alert_dismissal",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/code_security",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/dependabot_alerts",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/dependabot_security_updates",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/dependency_graph",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/dependency_graph_autosubmit_action",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/private_vulnerability_reporting",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/secret_protection",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/secret_scanning",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/secret_scanning_delegated_alert_dismissal",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/secret_scanning_extended_metadata",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/secret_scanning_generic_secrets",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/secret_scanning_non_provider_patterns",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/secret_scanning_push_protection",
    "operations/code-security/update-enterprise-configuration/requestBody/properties/secret_scanning_validity_checks"
})
public enum GitHubCodeSecurityEnablement {
    ENABLED("enabled"),
    DISABLED("disabled"),
    NOT_SET("not_set");

    private static final EnumUtils.WireIndex<GitHubCodeSecurityEnablement> WIRE_INDEX =
            EnumUtils.index(GitHubCodeSecurityEnablement.class, GitHubCodeSecurityEnablement::wireValue);

    private final String wireValue;

    GitHubCodeSecurityEnablement(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCodeSecurityEnablement fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubCodeSecurityEnablement value: " + value));
    }
}
