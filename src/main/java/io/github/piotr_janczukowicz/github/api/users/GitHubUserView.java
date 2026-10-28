package io.github.piotr_janczukowicz.github.api.users;

import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Public or private user response from get-user endpoints. */
@CatalogSchema({
    "operations/users/get-authenticated/200",
    "operations/users/get-by-id/200",
    "operations/users/get-by-username/200"
})
public sealed interface GitHubUserView permits GitHubUser, GitHubAuthenticatedUser {}
