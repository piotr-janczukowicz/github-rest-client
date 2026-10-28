package io.github.piotr_janczukowicz.github.api;

import io.github.piotr_janczukowicz.http.HttpStatus;

/** What a create-or-update secret call did, as GitHub reports it by the status of its answer. */
public enum GitHubSecretChange {

    /** GitHub created the secret (HTTP 201). */
    CREATED,

    /** GitHub replaced the value of an existing secret (HTTP 204). */
    UPDATED;

    /**
     * Change that a successful answer stands for.
     *
     * @param statusCode status of the successful response
     * @return {@link #CREATED} for HTTP 201, {@link #UPDATED} for any other success, which is HTTP 204 in the spec
     */
    public static GitHubSecretChange fromStatus(final int statusCode) {
        return HttpStatus.CREATED.hasCode(statusCode) ? CREATED : UPDATED;
    }
}
