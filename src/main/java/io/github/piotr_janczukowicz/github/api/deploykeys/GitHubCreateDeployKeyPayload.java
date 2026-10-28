package io.github.piotr_janczukowicz.github.api.deploykeys;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create deploy key.
 *
 * @param title A name for the key.
 * @param key The contents of the key.
 * @param readOnly If {@code true}, the key will only be able to read repository contents. Otherwise, the key will be
 *     able to read and write. Deploy keys with write access can perform the same actions as an organization member with
 *     admin access, or a collaborator on a personal repository. For more information, see "[Repository permission
 *     levels for an organization](https://docs.github.com/articles/repository-permission-levels-for-an-organization/)"
 *     and "[Permission levels for a user account
 *     repository](https://docs.github.com/articles/permission-levels-for-a-user-account-repository/)."
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-deploy-key/requestBody")
public record GitHubCreateDeployKeyPayload(
        @Nullable String title, String key, @Nullable Boolean readOnly) {

    public GitHubCreateDeployKeyPayload {
        requireNonNull(key, "key");
    }
}
