package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageDeleteSshResult
 *
 * @param hostname Hostname of the node where the SSH key was removed.
 * @param uuid UUID of the SSH key that was removed.
 * @param message Message indicating the result of the SSH key removal.
 * @param error Error message indicating the reason for the SSH key removal failure. This field is only present if the
 *     SSH key removal failed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-delete-ssh-response/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageDeleteSshResult(
        @Nullable String hostname,
        @Nullable String uuid,
        @Nullable String message,
        @Nullable String error) {}
