package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHub Pages HTTPS certificate state. */
@CatalogSchema("components/schemas/pages-https-certificate/properties/state")
public enum GitHubPagesHttpsCertificateState {
    NEW("new"),
    AUTHORIZATION_CREATED("authorization_created"),
    AUTHORIZATION_PENDING("authorization_pending"),
    AUTHORIZED("authorized"),
    AUTHORIZATION_REVOKED("authorization_revoked"),
    ISSUED("issued"),
    UPLOADED("uploaded"),
    APPROVED("approved"),
    ERRORED("errored"),
    BAD_AUTHZ("bad_authz"),
    DESTROY_PENDING("destroy_pending"),
    DNS_CHANGED("dns_changed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubPagesHttpsCertificateState> WIRE_INDEX =
            EnumUtils.index(GitHubPagesHttpsCertificateState.class, GitHubPagesHttpsCertificateState::wireValue);

    private final String wireValue;

    GitHubPagesHttpsCertificateState(final String wireValue) {
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
     * Certificate state from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubPagesHttpsCertificateState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
