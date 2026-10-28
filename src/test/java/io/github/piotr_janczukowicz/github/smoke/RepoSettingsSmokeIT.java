package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.actions.GitHubCreateRepoVariablePayload;
import io.github.piotr_janczukowicz.github.api.actions.GitHubUpdateRepoVariablePayload;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsPublicKey;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsVariable;
import io.github.piotr_janczukowicz.github.api.deploykeys.GitHubCreateDeployKeyPayload;
import io.github.piotr_janczukowicz.github.api.deploykeys.GitHubDeployKey;
import io.github.piotr_janczukowicz.github.api.releases.GitHubCreateReleasePayload;
import io.github.piotr_janczukowicz.github.api.releases.GitHubRelease;
import io.github.piotr_janczukowicz.github.api.releases.GitHubReleaseAsset;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCreateWebhookPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubHook;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubReplaceAllTopicsPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryTopics;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfig;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class RepoSettingsSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    private static String sshEd25519PublicKey() throws GeneralSecurityException {
        final byte[] encoded = KeyPairGenerator.getInstance("Ed25519")
                .generateKeyPair()
                .getPublic()
                .getEncoded();
        final byte[] raw = Arrays.copyOfRange(encoded, encoded.length - 32, encoded.length);
        final byte[] type = "ssh-ed25519".getBytes(StandardCharsets.US_ASCII);
        final ByteBuffer blob = ByteBuffer.allocate(4 + type.length + 4 + raw.length);
        blob.putInt(type.length).put(type).putInt(raw.length).put(raw);
        return "ssh-ed25519 " + Base64.getEncoder().encodeToString(blob.array());
    }

    @Test
    void release_shouldRoundTrip_whenADraftHasAnAsset() throws IOException {
        final GitHub github = ENV.github();
        final String owner = ENV.owner();
        final String repo = ENV.repo();
        final Path file = Files.createTempFile("smoke-", ".txt");
        Files.writeString(file, "asset");
        final GitHubRelease release = github.releases()
                .release()
                .createRelease(
                        owner,
                        repo,
                        new GitHubCreateReleasePayload(
                                ENV.name("v"), null, ENV.name("release"), "smoke", true, null, null, null, null));
        try {
            final GitHubReleaseAsset asset = github.releases()
                    .assets()
                    .uploadReleaseAsset(release.uploadUrl(), file, "text/plain", "smoke-asset.txt", null);
            try {
                final GitHubRelease fetched = github.releases()
                        .release()
                        .getRelease(owner, repo, release.id())
                        .orElseThrow();

                assertThat(asset.name()).isEqualTo("smoke-asset.txt");
                assertThat(asset.size()).isEqualTo(5);
                assertThat(fetched.draft()).isTrue();
            } finally {
                github.releases().assets().deleteReleaseAsset(owner, repo, asset.id());
            }
        } finally {
            github.releases().release().deleteRelease(owner, repo, release.id());
            Files.deleteIfExists(file);
        }
    }

    @Test
    void webhook_shouldRoundTrip_whenCreatedInactive() throws IOException {
        final GitHub github = ENV.github();
        final GitHubHook hook = github.repositories()
                .webhooks()
                .createWebhook(
                        ENV.owner(),
                        ENV.repo(),
                        new GitHubCreateWebhookPayload(
                                "web",
                                new GitHubWebhookConfig("https://example.invalid/smoke", "json", null, null),
                                List.of("push"),
                                false));
        try {
            final GitHubHook fetched = github.repositories()
                    .webhooks()
                    .getWebhook(ENV.owner(), ENV.repo(), hook.id())
                    .orElseThrow();

            assertThat(fetched.active()).isFalse();
            assertThat(fetched.config().url()).isEqualTo("https://example.invalid/smoke");
        } finally {
            github.repositories().webhooks().deleteWebhook(ENV.owner(), ENV.repo(), hook.id());
        }
    }

    @Test
    void deployKey_shouldRoundTrip_whenReadOnly() throws IOException, GeneralSecurityException {
        final GitHub github = ENV.github();
        final GitHubDeployKey key = github.deployKeys()
                .createDeployKey(
                        ENV.owner(),
                        ENV.repo(),
                        new GitHubCreateDeployKeyPayload(ENV.name("key"), sshEd25519PublicKey(), true));
        try {
            final GitHubDeployKey fetched = github.deployKeys()
                    .getDeployKey(ENV.owner(), ENV.repo(), key.id())
                    .orElseThrow();

            assertThat(fetched.title()).isEqualTo(ENV.name("key"));
            assertThat(fetched.readOnly()).isTrue();
        } finally {
            github.deployKeys().deleteDeployKey(ENV.owner(), ENV.repo(), key.id());
        }
    }

    @Test
    void actionsVariable_shouldRoundTrip_whenCreatedUpdatedAndDeleted() throws IOException {
        final GitHub github = ENV.github();
        final String name = "SMOKE_" + ENV.runId().toUpperCase(Locale.ROOT) + "_VAR";
        github.actions()
                .variables()
                .createRepoVariable(ENV.owner(), ENV.repo(), new GitHubCreateRepoVariablePayload(name, "one"));
        try {
            github.actions()
                    .variables()
                    .updateRepoVariable(
                            ENV.owner(), ENV.repo(), name, new GitHubUpdateRepoVariablePayload(null, "two"));
            final Optional<GitHubActionsVariable> variable =
                    github.actions().variables().getRepoVariable(ENV.owner(), ENV.repo(), name);

            assertThat(variable.orElseThrow().value()).isEqualTo("two");
        } finally {
            github.actions().variables().deleteRepoVariable(ENV.owner(), ENV.repo(), name);
        }
    }

    @Test
    void getRepoPublicKey_shouldReturnAKey_whenReadingSecretsSetup() throws IOException {
        final GitHubActionsPublicKey key = ENV.github().actions().secrets().getRepoPublicKey(ENV.owner(), ENV.repo());

        assertThat(key.keyId()).isNotBlank();
        assertThat(Base64.getDecoder().decode(key.key())).hasSize(32);
    }

    @Test
    void topics_shouldBeReplaceable_whenRestoredAfterwards() throws IOException {
        final GitHub github = ENV.github();
        final List<String> previous = github.repositories()
                .repository()
                .getAllTopics(ENV.owner(), ENV.repo())
                .names();
        try {
            final GitHubRepositoryTopics replaced = github.repositories()
                    .repository()
                    .replaceAllTopics(
                            ENV.owner(), ENV.repo(), new GitHubReplaceAllTopicsPayload(List.of("smoke-test")));

            assertThat(replaced.names()).containsExactly("smoke-test");
        } finally {
            github.repositories()
                    .repository()
                    .replaceAllTopics(ENV.owner(), ENV.repo(), new GitHubReplaceAllTopicsPayload(previous));
        }
    }
}
