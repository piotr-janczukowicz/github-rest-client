package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Adds one or more email addresses to your GitHub account.
 *
 * @param emails Adds one or more email addresses to your GitHub account. Must contain at least one email address.
 *     **Note:** Alternatively, you can pass a single email address or an `array` of emails addresses directly, but we
 *     recommend that you pass an object using the `emails` key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/users/add-email-for-authenticated-user/requestBody",
    "operations/users/delete-email-for-authenticated-user/requestBody"
})
public record GitHubUpdateEmailsPayload(List<String> emails) {

    public GitHubUpdateEmailsPayload {
        emails = List.copyOf(requireNonEmpty(emails, "emails"));
    }
}
