package io.github.piotr_janczukowicz.github.api;

import java.util.regex.Pattern;

/**
 * Shared OpenAPI string patterns for GitHub REST request bodies and parameters.
 *
 * <p>All spec {@code pattern} values that github-api validates live here. Regex text is copied verbatim from the spec,
 * with only Java string escaping added.
 */
public final class GitHubSchemaPatterns {

    /** Artifact metadata {@code cluster} path parameter. Equivalent to ARTIFACT_GITHUB_REPOSITORY; kept separate. */
    public static final Pattern ARTIFACT_CLUSTER = Pattern.compile("^[a-zA-Z0-9._-]+$");

    /** Artifact {@code digest} and {@code subject_digest} fields, sha256 hex. */
    public static final Pattern ARTIFACT_DIGEST = Pattern.compile("^sha256:[a-f0-9]{64}$");

    /** Artifact {@code githubRepository} field. Same character set as ARTIFACT_CLUSTER; kept separate. */
    public static final Pattern ARTIFACT_GITHUB_REPOSITORY = Pattern.compile("^[A-Za-z0-9.\\-_]+$");

    /** Code scanning SARIF upload {@code ref} field: refs/heads, refs/tags or refs/pull only. */
    public static final Pattern CODE_SCANNING_REF = Pattern.compile("^refs/(heads|tags|pull)/.*$");

    /** Git commit {@code commitSha} field: 40-hex SHA-1, optionally followed by 24 more hex digits (SHA-256). */
    public static final Pattern COMMIT_SHA = Pattern.compile("^([0-9a-fA-F]{40}(?:[0-9a-fA-F]{24})?)$");

    /** Hosted runner custom image {@code version} path parameter: "Version of a custom image". */
    public static final Pattern CUSTOM_IMAGE_VERSION = Pattern.compile("^\\d+\\.\\d+\\.\\d+$");

    /** GitHub libsodium-encrypted secret value, base64. */
    public static final Pattern ENCRYPTED_VALUE =
            Pattern.compile("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$");

    /** External custom properties {@code displayName} field: letters and digits only. */
    public static final Pattern EXTERNAL_PROPERTIES_DISPLAY_NAME = Pattern.compile("^[a-zA-Z0-9]+$");

    /** Dependency snapshot {@code ref} field: must start with {@code refs/}. */
    public static final Pattern GIT_REF = Pattern.compile("^refs/");

    /** Artifact storage record {@code registryUrl} and {@code artifactUrl} fields: must start with {@code https://}. */
    public static final Pattern HTTPS_URL = Pattern.compile("^https://");

    /** Dependency {@code packageUrl} (PURL) field: must start with {@code pkg}. */
    public static final Pattern PACKAGE_URL = Pattern.compile("^pkg");

    /** Public SSH {@code key} field: RSA, DSS, Ed25519 or ECDSA key type prefix followed by a space. */
    public static final Pattern SSH_PUBLIC_KEY =
            Pattern.compile("^ssh-(rsa|dss|ed25519) |^ecdsa-sha2-nistp(256|384|521) ");

    /** SSH signing {@code key} field: as SSH_PUBLIC_KEY, plus sk-* security key types ending in @openssh.com. */
    public static final Pattern SSH_SIGNING_KEY =
            Pattern.compile("^ssh-(rsa|dss|ed25519) |^ecdsa-sha2-nistp(256|384|521) "
                    + "|^(sk-ssh-ed25519|sk-ecdsa-sha2-nistp256)@openssh.com ");

    private GitHubSchemaPatterns() {
        throw new UnsupportedOperationException("Utility class");
    }
}
