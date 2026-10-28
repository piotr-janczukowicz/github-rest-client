package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHub Pages protected domain state. */
@CatalogSchema("components/schemas/page/properties/protected_domain_state")
public enum GitHubPagesProtectedDomainState {
    PENDING("pending"),
    VERIFIED("verified"),
    UNVERIFIED("unverified"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubPagesProtectedDomainState> WIRE_INDEX =
            EnumUtils.index(GitHubPagesProtectedDomainState.class, GitHubPagesProtectedDomainState::wireValue);

    private final String wireValue;

    GitHubPagesProtectedDomainState(final String wireValue) {
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
     * Protected domain state from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubPagesProtectedDomainState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
