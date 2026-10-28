package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Integration app permissions.
 *
 * @param issues Issues permission level
 * @param checks Checks permission level
 * @param metadata Metadata permission level
 * @param contents Contents permission level
 * @param deployments Deployments permission level
 * @param additionalProperties Permission levels for keys outside the named schema properties
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/integration/properties/permissions")
public record GitHubIntegrationPermissions(
        @Nullable String issues,
        @Nullable String checks,
        @Nullable String metadata,
        @Nullable String contents,
        @Nullable String deployments,
        @JsonIgnore Map<String, String> additionalProperties) {

    public GitHubIntegrationPermissions {
        requireNonNull(additionalProperties, "additionalProperties");
    }

    /**
     * Additional permission keys serialized at the top level.
     *
     * @return additional permission levels
     */
    @JsonAnyGetter
    public Map<String, String> additionalPropertiesForJson() {
        return additionalProperties;
    }
}
