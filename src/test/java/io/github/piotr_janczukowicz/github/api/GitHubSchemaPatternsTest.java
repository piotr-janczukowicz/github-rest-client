package io.github.piotr_janczukowicz.github.api;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_CLUSTER;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_DIGEST;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_GITHUB_REPOSITORY;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.CODE_SCANNING_REF;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.COMMIT_SHA;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.CUSTOM_IMAGE_VERSION;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.EXTERNAL_PROPERTIES_DISPLAY_NAME;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.GIT_REF;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.HTTPS_URL;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.PACKAGE_URL;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.SSH_PUBLIC_KEY;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.SSH_SIGNING_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.lang.reflect.Constructor;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubSchemaPatternsTest {

    private static final String HEX_39 = "0123456789abcdef0123456789abcdef0123456";
    private static final String HEX_40 = "0123456789abcdef0123456789abcdef01234567";
    private static final String HEX_41 = HEX_40 + "0";
    private static final String HEX_63 = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcde";
    private static final String HEX_64 = "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef";
    private static final String HEX_65 = HEX_64 + "0";
    private static final String SSH_ED25519_KEY = "AAAAC3NzaC1lZDI1NTE5AAAAIExampleKeyMaterial";
    private static final String SK_SSH_ED25519_LINE = "sk-ssh-ed25519@openssh.com " + SSH_ED25519_KEY;

    @Test
    void constructor_shouldThrow_whenInvoked() throws Exception {
        final Constructor<GitHubSchemaPatterns> constructor = GitHubSchemaPatterns.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        final Throwable thrown = catchThrowable(constructor::newInstance);

        assertThat(thrown).hasCauseInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void encryptedValue_shouldMatchBase64Only() {
        assertThat(GitHubSchemaPatterns.ENCRYPTED_VALUE.matcher("YWJjZA==").matches())
                .isTrue();
        assertThat(GitHubSchemaPatterns.ENCRYPTED_VALUE.matcher("not base64!").matches())
                .isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"my-cluster.01_prod", "ClusterA"})
    void artifactCluster_shouldMatch_whenValueIsValid(final String value) {
        assertThat(containsMatch(ARTIFACT_CLUSTER, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"my cluster", "cluster/prod", ""})
    void artifactCluster_shouldNotMatch_whenValueIsInvalid(final String value) {
        assertThat(containsMatch(ARTIFACT_CLUSTER, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"sha256:" + HEX_64})
    void artifactDigest_shouldMatch_whenValueIsSha256Digest(final String value) {
        assertThat(containsMatch(ARTIFACT_DIGEST, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "sha256:" + HEX_63,
                "sha512:" + HEX_64,
                "sha256:" + HEX_64 + "00",
                "sha256:0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF"
            })
    void artifactDigest_shouldNotMatch_whenValueIsNotSha256Digest(final String value) {
        assertThat(containsMatch(ARTIFACT_DIGEST, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"my.repo-name_1", "Repo"})
    void artifactGithubRepository_shouldMatch_whenValueIsValid(final String value) {
        assertThat(containsMatch(ARTIFACT_GITHUB_REPOSITORY, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"my repo", "repo/name", ""})
    void artifactGithubRepository_shouldNotMatch_whenValueIsInvalid(final String value) {
        assertThat(containsMatch(ARTIFACT_GITHUB_REPOSITORY, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"refs/heads/x", "refs/tags/v1.0.0", "refs/pull/42/merge", "refs/pull/42/head"})
    void codeScanningRef_shouldMatch_whenRefIsHeadsTagsOrPull(final String value) {
        assertThat(containsMatch(CODE_SCANNING_REF, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"refs/foo", "refs/heads", "heads/x", "main"})
    void codeScanningRef_shouldNotMatch_whenRefIsNotHeadsTagsOrPull(final String value) {
        assertThat(containsMatch(CODE_SCANNING_REF, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {HEX_40, HEX_64})
    void commitSha_shouldMatch_whenValueIsSha1OrSha256Hex(final String value) {
        assertThat(containsMatch(COMMIT_SHA, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {HEX_39, HEX_41, HEX_65, "g" + HEX_39, ""})
    void commitSha_shouldNotMatch_whenLengthOrCharacterIsInvalid(final String value) {
        assertThat(containsMatch(COMMIT_SHA, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.0.0", "10.20.300"})
    void customImageVersion_shouldMatch_whenValueIsMajorMinorPatch(final String value) {
        assertThat(containsMatch(CUSTOM_IMAGE_VERSION, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.0", "v1.0.0", "1.0.0-beta", "1.0.0.0", ""})
    void customImageVersion_shouldNotMatch_whenValueIsNotMajorMinorPatch(final String value) {
        assertThat(containsMatch(CUSTOM_IMAGE_VERSION, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Team123", "A", "ABC123XYZ"})
    void externalPropertiesDisplayName_shouldMatch_whenValueIsAlphanumeric(final String value) {
        assertThat(containsMatch(EXTERNAL_PROPERTIES_DISPLAY_NAME, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Team 123", "Team-Name", "Team_1", ""})
    void externalPropertiesDisplayName_shouldNotMatch_whenValueHasNonAlphanumeric(final String value) {
        assertThat(containsMatch(EXTERNAL_PROPERTIES_DISPLAY_NAME, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"refs/heads/main", "refs/tags/v1", "refs/"})
    void gitRef_shouldMatch_whenValueStartsWithRefs(final String value) {
        assertThat(containsMatch(GIT_REF, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"heads/main", "xrefs/heads/main", ""})
    void gitRef_shouldNotMatch_whenValueDoesNotStartWithRefs(final String value) {
        assertThat(containsMatch(GIT_REF, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://registry.example.com/artifact", "https://"})
    void httpsUrl_shouldMatch_whenValueStartsWithHttps(final String value) {
        assertThat(containsMatch(HTTPS_URL, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://registry.example.com", "ftp://registry.example.com", "registry.example.com"})
    void httpsUrl_shouldNotMatch_whenValueDoesNotStartWithHttps(final String value) {
        assertThat(containsMatch(HTTPS_URL, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"pkg:maven/org.example/lib@1.0.0", "pkg:npm/left-pad@1.3.0"})
    void packageUrl_shouldMatch_whenValueStartsWithPkg(final String value) {
        assertThat(containsMatch(PACKAGE_URL, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"maven:pkg", "PKG:npm/x", "npm/left-pad"})
    void packageUrl_shouldNotMatch_whenValueDoesNotStartWithPkg(final String value) {
        assertThat(containsMatch(PACKAGE_URL, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "ssh-ed25519 " + SSH_ED25519_KEY,
                "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQ user@example",
                "ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTY"
            })
    void sshPublicKey_shouldMatch_whenKeyTypeIsFollowedBySpace(final String value) {
        assertThat(containsMatch(SSH_PUBLIC_KEY, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(
            strings = {"ssh-ed25519" + SSH_ED25519_KEY, "ssh-ed25519", "ecdsa-sha2-nistp256AAAA", SK_SSH_ED25519_LINE})
    void sshPublicKey_shouldNotMatch_whenKeyTypeIsNotFollowedBySpace(final String value) {
        assertThat(containsMatch(SSH_PUBLIC_KEY, value)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "ssh-ed25519 " + SSH_ED25519_KEY,
                "ecdsa-sha2-nistp384 AAAAE2VjZHNhLXNoYTItbmlzdHAzODQ",
                SK_SSH_ED25519_LINE,
                "sk-ecdsa-sha2-nistp256@openssh.com AAAAInNrLWVjZHNh"
            })
    void sshSigningKey_shouldMatch_whenKeyTypeIsStandardOrSecurityKey(final String value) {
        assertThat(containsMatch(SSH_SIGNING_KEY, value)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "ssh-ed25519" + SSH_ED25519_KEY,
                "sk-ssh-ed25519@openssh.com",
                "sk-ed25519@openssh.com AAAA",
                "sk-ssh-ed25519@openssh.comAAAA"
            })
    void sshSigningKey_shouldNotMatch_whenKeyTypeOrSeparatorIsInvalid(final String value) {
        assertThat(containsMatch(SSH_SIGNING_KEY, value)).isFalse();
    }

    @Test
    void sshSigningKey_shouldMatch_whenSecurityKeyLine() {
        assertThat(containsMatch(SSH_SIGNING_KEY, SK_SSH_ED25519_LINE)).isTrue();
    }

    @Test
    void sshPublicKey_shouldNotMatch_whenSecurityKeyLine() {
        assertThat(containsMatch(SSH_PUBLIC_KEY, SK_SSH_ED25519_LINE)).isFalse();
    }

    private static boolean containsMatch(final Pattern pattern, final String value) {
        return pattern.matcher(value).find();
    }
}
