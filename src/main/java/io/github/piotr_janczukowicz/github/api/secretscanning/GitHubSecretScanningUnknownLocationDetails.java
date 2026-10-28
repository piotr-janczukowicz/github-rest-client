package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;

/**
 * Details of a location whose type this library version does not know
 * ({@link GitHubSecretScanningLocationType#UNKNOWN}), kept as GitHub sent them so that one new location type does not
 * fail the whole alert or list it is in.
 *
 * <p>The map is a shallow, unmodifiable copy in JSON order; nested objects and arrays are the plain maps and lists that
 * were decoded. It is not part of the OpenAPI schema: a location type that has its own record is never decoded into
 * this one.
 *
 * <p>As a {@link GitHubSecretScanningFirstDetectedLocation} it is the fallback for a location object that matches none
 * of the known shapes.
 *
 * @param properties the properties of the details object, by name
 */
public record GitHubSecretScanningUnknownLocationDetails(
        @JsonValue Map<String, Object> properties)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningUnknownLocationDetails {
        requireNonNull(properties, "properties");
    }
}
