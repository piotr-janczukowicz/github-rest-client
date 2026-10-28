package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub Users REST resource aggregator for API version {@code 2026-03-10}.
 *
 * <p>Interact with and view information about users and also current user.
 */
public final class GitHubUsersApi {

    private final LazyValue<GitHubUserApi> user;
    private final LazyValue<GitHubUsersBlockingApi> blocking;
    private final LazyValue<GitHubUsersEmailsApi> emails;
    private final LazyValue<GitHubUsersFollowersApi> followers;
    private final LazyValue<GitHubUsersGpgKeysApi> gpgKeys;
    private final LazyValue<GitHubUsersKeysApi> keys;
    private final LazyValue<GitHubUsersSocialAccountsApi> socialAccounts;
    private final LazyValue<GitHubUsersSshSigningKeysApi> sshSigningKeys;
    private final LazyValue<GitHubUsersAttestationsApi> attestations;

    /**
     * Users resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.user = new LazyValue<>(() -> new GitHubUserApi(caller));
        this.blocking = new LazyValue<>(() -> new GitHubUsersBlockingApi(caller));
        this.emails = new LazyValue<>(() -> new GitHubUsersEmailsApi(caller));
        this.followers = new LazyValue<>(() -> new GitHubUsersFollowersApi(caller));
        this.gpgKeys = new LazyValue<>(() -> new GitHubUsersGpgKeysApi(caller));
        this.keys = new LazyValue<>(() -> new GitHubUsersKeysApi(caller));
        this.socialAccounts = new LazyValue<>(() -> new GitHubUsersSocialAccountsApi(caller));
        this.sshSigningKeys = new LazyValue<>(() -> new GitHubUsersSshSigningKeysApi(caller));
        this.attestations = new LazyValue<>(() -> new GitHubUsersAttestationsApi(caller));
    }

    /**
     * User operations ({@code subcategory=users}).
     *
     * @return user API
     */
    public GitHubUserApi user() {
        return user.get();
    }

    /**
     * User blocking operations ({@code subcategory=blocking}).
     *
     * @return blocking API
     */
    public GitHubUsersBlockingApi blocking() {
        return blocking.get();
    }

    /**
     * User email operations ({@code subcategory=emails}).
     *
     * @return emails API
     */
    public GitHubUsersEmailsApi emails() {
        return emails.get();
    }

    /**
     * User follower operations ({@code subcategory=followers}).
     *
     * @return followers API
     */
    public GitHubUsersFollowersApi followers() {
        return followers.get();
    }

    /**
     * User GPG key operations ({@code subcategory=gpg-keys}).
     *
     * @return GPG keys API
     */
    public GitHubUsersGpgKeysApi gpgKeys() {
        return gpgKeys.get();
    }

    /**
     * User public SSH key operations ({@code subcategory=keys}).
     *
     * @return keys API
     */
    public GitHubUsersKeysApi keys() {
        return keys.get();
    }

    /**
     * User social account operations ({@code subcategory=social-accounts}).
     *
     * @return social accounts API
     */
    public GitHubUsersSocialAccountsApi socialAccounts() {
        return socialAccounts.get();
    }

    /**
     * User SSH signing key operations ({@code subcategory=ssh-signing-keys}).
     *
     * @return SSH signing keys API
     */
    public GitHubUsersSshSigningKeysApi sshSigningKeys() {
        return sshSigningKeys.get();
    }

    /**
     * User attestation operations ({@code subcategory=attestations}).
     *
     * @return attestations API
     */
    public GitHubUsersAttestationsApi attestations() {
        return attestations.get();
    }
}
