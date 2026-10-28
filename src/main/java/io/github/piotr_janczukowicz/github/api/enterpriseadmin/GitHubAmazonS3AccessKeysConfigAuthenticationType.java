package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Authentication type for Amazon S3 with access keys. */
@CatalogSchema("components/schemas/amazon-s3-access-keys-config/properties/authentication_type")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubAmazonS3AccessKeysConfigAuthenticationType {
    /** Access keys. */
    ACCESS_KEYS("access_keys");

    private static final EnumUtils.WireIndex<GitHubAmazonS3AccessKeysConfigAuthenticationType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubAmazonS3AccessKeysConfigAuthenticationType.class,
                    GitHubAmazonS3AccessKeysConfigAuthenticationType::wireValue);

    private final String wireValue;

    GitHubAmazonS3AccessKeysConfigAuthenticationType(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAmazonS3AccessKeysConfigAuthenticationType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub amazon s3 access keys config authentication type: " + value));
    }
}
