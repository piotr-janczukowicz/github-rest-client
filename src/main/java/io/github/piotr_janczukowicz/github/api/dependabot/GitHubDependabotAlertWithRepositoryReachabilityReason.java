package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The reason for the reachability verdict. This field is omitted when reachability analysis is disabled or no result
 * has been computed.
 */
@CatalogSchema("components/schemas/dependabot-alert-with-repository/properties/reachability/properties/reason")
public enum GitHubDependabotAlertWithRepositoryReachabilityReason {
    UNKNOWN("unknown"),
    UNAVAILABLE_UNSUPPORTED_ECOSYSTEM("unavailable_unsupported_ecosystem"),
    UNAVAILABLE_ANALYSIS_TIMED_OUT("unavailable_analysis_timed_out"),
    POTENTIALLY_REACHABLE_INVALID_MANIFEST_PATH("potentially_reachable_invalid_manifest_path"),
    POTENTIALLY_REACHABLE_MISSING_VULNERABLE_FUNCTION_ANNOTATION(
            "potentially_reachable_missing_vulnerable_function_annotation"),
    POTENTIALLY_REACHABLE_DEPENDENCY_NOT_FOUND("potentially_reachable_dependency_not_found"),
    POTENTIALLY_REACHABLE_DEPENDENCY_ANALYSIS_INCOMPLETE("potentially_reachable_dependency_analysis_incomplete"),
    POTENTIALLY_REACHABLE_REPOSITORY_ANALYSIS_INCOMPLETE("potentially_reachable_repository_analysis_incomplete"),
    POTENTIALLY_REACHABLE_CALL_FOUND_OUTSIDE_MANIFEST_SCOPE("potentially_reachable_call_found_outside_manifest_scope"),
    POTENTIALLY_REACHABLE_UNCLASSIFIED_ANALYSIS_STATE("potentially_reachable_unclassified_analysis_state"),
    REACHABLE_VULNERABLE_FUNCTION_CALLED("reachable_vulnerable_function_called"),
    UNREACHABLE_VULNERABLE_FUNCTION_NOT_CALLED("unreachable_vulnerable_function_not_called"),
    POTENTIALLY_REACHABLE_UNRESOLVED_DYNAMIC_CALL("potentially_reachable_unresolved_dynamic_call"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertWithRepositoryReachabilityReason> WIRE_INDEX =
            EnumUtils.index(
                    GitHubDependabotAlertWithRepositoryReachabilityReason.class,
                    GitHubDependabotAlertWithRepositoryReachabilityReason::wireValue);

    private final String wireValue;

    GitHubDependabotAlertWithRepositoryReachabilityReason(final String wireValue) {
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
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDependabotAlertWithRepositoryReachabilityReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
