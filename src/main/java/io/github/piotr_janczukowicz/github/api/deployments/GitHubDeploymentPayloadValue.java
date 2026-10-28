package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/** Deployment payload ({@code oneOf} object vs string). */
@CatalogSchema({
    "components/schemas/deployment/properties/payload",
    "operations/repos/create-deployment/requestBody/properties/payload"
})
public record GitHubDeploymentPayloadValue(
        @Nullable String textValue, @Nullable Map<String, Object> objectValue) {

    public GitHubDeploymentPayloadValue {
        final boolean hasText = textValue != null;
        final boolean hasObject = objectValue != null;

        if (hasText == hasObject) {
            throw new IllegalArgumentException("exactly one of textValue or objectValue is required");
        }
        if (objectValue != null) {
            objectValue = Collections.unmodifiableMap(new LinkedHashMap<>(objectValue));
        }
    }

    /**
     * Payload as a JSON string.
     *
     * @param text JSON string
     * @return payload value
     */
    public static GitHubDeploymentPayloadValue ofText(final String text) {
        return new GitHubDeploymentPayloadValue(requireNonNull(text, "text"), null);
    }

    /**
     * Payload as a JSON object.
     *
     * @param object JSON object
     * @return payload value
     */
    public static GitHubDeploymentPayloadValue ofObject(final Map<String, Object> object) {
        return new GitHubDeploymentPayloadValue(null, requireNonNull(object, "object"));
    }
}
