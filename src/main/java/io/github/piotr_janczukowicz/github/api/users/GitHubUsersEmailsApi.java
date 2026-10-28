package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/** GitHub user email REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersEmailsApi extends GitHubResourceApi {

    private static final PathTemplate EMAILS = PathTemplate.of("/user/emails");
    private static final PathTemplate EMAIL_VISIBILITY = PathTemplate.of("/user/email/visibility");
    private static final PathTemplate PUBLIC_EMAILS = PathTemplate.of("/user/public_emails");

    private static final JsonType<List<GitHubEmail>> EMAILS_TYPE = JsonTypes.listOf(GitHubEmail.class);

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersEmailsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Set primary email visibility for the authenticated user ({@code PATCH /user/email/visibility}).
     *
     * @param visibility primary email visibility
     * @return email addresses after the update
     * @throws NullPointerException if {@code visibility} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubEmail> setPrimaryEmailVisibility(final GitHubEmailVisibility visibility) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(visibility, "visibility");

        return setPrimaryEmailVisibility(new GitHubSetPrimaryEmailVisibilityPayload(visibility));
    }

    /**
     * Set primary email visibility for the authenticated user ({@code PATCH /user/email/visibility}).
     *
     * @param payload primary email visibility
     * @return email addresses after the update
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubEmail> setPrimaryEmailVisibility(final GitHubSetPrimaryEmailVisibilityPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(EMAIL_VISIBILITY), payload), EMAILS_TYPE);
    }

    /**
     * List email addresses for the authenticated user ({@code GET /user/emails}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listEmails(GitHubPageRequest)}.
     *
     * @return first HTTP page of emails
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEmail> listEmails() throws IOException {
        return listEmails(GitHubPageRequest.byPage());
    }

    /**
     * List email addresses for the authenticated user ({@code GET /user/emails}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of emails
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEmail> listEmails(final GitHubPageRequest request) throws IOException {
        return pageList(EMAILS, Map.of(), request, EMAILS_TYPE);
    }

    /**
     * Add an email address for the authenticated user ({@code POST /user/emails}).
     *
     * @param email email address to add
     * @return email addresses after the update
     * @throws IllegalArgumentException if {@code email} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubEmail> addEmail(final String email) throws IOException {
        return addEmails(List.of(requireNotBlank(email, "email")));
    }

    /**
     * Add email addresses for the authenticated user ({@code POST /user/emails}).
     *
     * @param emails email addresses to add
     * @return email addresses after the update
     * @throws NullPointerException if {@code emails} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubEmail> addEmails(final List<String> emails) throws IOException {
        requireNonNull(emails, "emails");

        return addEmails(new GitHubUpdateEmailsPayload(emails));
    }

    /**
     * Add email addresses for the authenticated user ({@code POST /user/emails}).
     *
     * <p>Always sends the object form with an {@code emails} array (OpenAPI also allows a bare string or array).
     *
     * @param payload email addresses to add
     * @return email addresses after the update
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubEmail> addEmails(final GitHubUpdateEmailsPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(EMAILS), payload), EMAILS_TYPE);
    }

    /**
     * Delete an email address for the authenticated user ({@code DELETE /user/emails}).
     *
     * @param email email address to delete
     * @throws IllegalArgumentException if {@code email} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteEmail(final String email) throws IOException {
        deleteEmails(List.of(requireNotBlank(email, "email")));
    }

    /**
     * Delete email addresses for the authenticated user ({@code DELETE /user/emails}).
     *
     * @param emails email addresses to delete
     * @throws NullPointerException if {@code emails} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteEmails(final List<String> emails) throws IOException {
        requireNonNull(emails, "emails");

        deleteEmails(new GitHubUpdateEmailsPayload(emails));
    }

    /**
     * Delete email addresses for the authenticated user ({@code DELETE /user/emails}).
     *
     * <p>Always sends the object form with an {@code emails} array (OpenAPI also allows a bare string or array).
     *
     * @param payload email addresses to delete
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteEmails(final GitHubUpdateEmailsPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(EMAILS), payload));
    }

    /**
     * List public email addresses for the authenticated user ({@code GET /user/public_emails}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPublicEmails(GitHubPageRequest)}.
     *
     * @return first HTTP page of public emails
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEmail> listPublicEmails() throws IOException {
        return listPublicEmails(GitHubPageRequest.byPage());
    }

    /**
     * List public email addresses for the authenticated user ({@code GET /user/public_emails}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of public emails
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubEmail> listPublicEmails(final GitHubPageRequest request) throws IOException {
        return pageList(PUBLIC_EMAILS, Map.of(), request, EMAILS_TYPE);
    }
}
