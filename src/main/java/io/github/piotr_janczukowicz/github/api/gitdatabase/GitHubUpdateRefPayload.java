package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update reference.
 *
 * @param sha The SHA1 value to set this reference to
 * @param force Indicates whether to force the update or to make sure the update is a fast-forward update. Leaving this
 *     out or setting it to {@code false} will make sure you're not overwriting work.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/update-ref/requestBody")
public record GitHubUpdateRefPayload(String sha, @Nullable Boolean force) {

    public GitHubUpdateRefPayload {
        requireNonNull(sha, "sha");
    }
}
