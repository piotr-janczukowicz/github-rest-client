package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;

import io.github.piotr_janczukowicz.http.HttpHeaders;

/**
 * Settings shared by the SCIM provisioning REST resources: the SCIM media type and the {@code Accept} header that
 * requests it.
 */
public final class GitHubScimSettings {

    /** The SCIM media type sent in the {@code Accept} header. */
    public static final String MEDIA_TYPE = "application/scim+json";

    /** The headers that request a SCIM response. */
    public static final HttpHeaders HEADERS = HttpHeaders.of(ACCEPT, MEDIA_TYPE);

    private GitHubScimSettings() {}
}
