package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSetSshResult
 *
 * @param hostname Hostname of the node where the SSH key was added.
 * @param uuid UUID of the SSH key that was added.
 * @param message Message indicating the result of the SSH key addition.
 * @param error Error message indicating the reason for the SSH key addition failure. This field is only present if the
 *     SSH key addition failed.
 * @param modified Whether the SSH key was already present on the `authorized keys` file.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-set-ssh-response/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSetSshResult(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable String message,
        @Nullable String error,
        @Nullable Boolean modified) {}
