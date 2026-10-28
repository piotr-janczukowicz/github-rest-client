package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** platform */
@CatalogSchema("components/schemas/ghes-version/items/properties/version/properties/platform")
@Flavors(GitHubFlavor.GHES)
public enum GitHubManageVersionVersionPlatform {
    AMI("ami"),
    AZURE("azure"),
    ESX("esx"),
    GCE("gce"),
    HYPERV("hyperv"),
    KVM("kvm"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubManageVersionVersionPlatform> WIRE_INDEX =
            EnumUtils.index(GitHubManageVersionVersionPlatform.class, GitHubManageVersionVersionPlatform::wireValue);

    private final String wireValue;

    GitHubManageVersionVersionPlatform(final String wireValue) {
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
     * platform from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubManageVersionVersionPlatform fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
