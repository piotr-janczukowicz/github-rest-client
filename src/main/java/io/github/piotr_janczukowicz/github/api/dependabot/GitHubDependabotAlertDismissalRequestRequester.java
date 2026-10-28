package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The user who requested the dismissal.
 *
 * @param id The unique identifier of the user.
 * @param login The login name of the user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-alert-dismissal-request-simple/properties/requester")
public record GitHubDependabotAlertDismissalRequestRequester(
        @Nullable Long id, @Nullable String login) {}
