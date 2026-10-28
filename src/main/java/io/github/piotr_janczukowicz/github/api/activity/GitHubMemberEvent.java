package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Member event payload.
 *
 * @param action The action that was performed
 * @param member The member that was added or removed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/member-event")
@RecordBuilder
public record GitHubMemberEvent(String action, GitHubSimpleUser member) implements GitHubEventPayload {

    public GitHubMemberEvent {
        requireNonNull(action, "action");
        requireNonNull(member, "member");
    }
}
