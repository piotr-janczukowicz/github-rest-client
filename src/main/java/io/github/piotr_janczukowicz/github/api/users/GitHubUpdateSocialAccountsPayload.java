package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Update Social Accounts.
 *
 * @param accountUrls Full URLs for the social media profiles to add.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/users/add-social-account-for-authenticated-user/requestBody",
    "operations/users/delete-social-account-for-authenticated-user/requestBody"
})
@RecordBuilder
public record GitHubUpdateSocialAccountsPayload(List<String> accountUrls) {

    public GitHubUpdateSocialAccountsPayload {
        accountUrls = List.copyOf(requireNonNull(accountUrls, "accountUrls"));
    }
}
