package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Transfer repository.
 *
 * @param newOwner The username or organization name the repository will be transferred to.
 * @param newName The new name to be given to the repository.
 * @param teamIds IDs of teams to add to the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/transfer/requestBody")
public record GitHubTransferPayload(
        String newOwner, @Nullable String newName, @Nullable List<Long> teamIds) {

    public GitHubTransferPayload {
        requireNonNull(newOwner, "newOwner");
        if (teamIds != null) {
            teamIds = List.copyOf(teamIds);
        }
    }
}
