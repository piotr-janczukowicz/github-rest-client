package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** API Insights actor type path parameter. */
@CatalogSchema("components/parameters/api-insights-actor-type/schema")
public enum GitHubApiInsightsActorType {
    INSTALLATION("installation"),
    CLASSIC_PAT("classic_pat"),
    FINE_GRAINED_PAT("fine_grained_pat"),
    OAUTH_APP("oauth_app"),
    GITHUB_APP_USER_TO_SERVER("github_app_user_to_server");

    private static final EnumUtils.WireIndex<GitHubApiInsightsActorType> WIRE_INDEX =
            EnumUtils.index(GitHubApiInsightsActorType.class, GitHubApiInsightsActorType::wireValue);

    private final String wireValue;

    GitHubApiInsightsActorType(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in path parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Actor type from a wire value.
     *
     * @param value path parameter value
     * @return matching actor type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known actor type
     */
    @JsonCreator
    public static GitHubApiInsightsActorType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub API insights actor type: " + value));
    }
}
