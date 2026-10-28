package io.github.piotr_janczukowicz.github.api.meta;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Response of {@code GET /meta}: the IP ranges and host keys GitHub uses, for firewall allow lists and SSH host
 * verification. A {@code null} list or group was not sent; lists are unmodifiable copies.
 *
 * @param verifiablePasswordAuthentication whether password authentication is verifiable on this instance
 * @param sshKeyFingerprints SHA-256 fingerprints of the SSH host keys
 * @param sshKeys SSH host public keys
 * @param hooks IP ranges that webhook deliveries come from
 * @param githubEnterpriseImporter IP ranges of the GitHub Enterprise Importer
 * @param web IP ranges used for web traffic
 * @param api IP ranges used for API traffic
 * @param git IP ranges used for Git traffic
 * @param packages IP ranges of GitHub Packages
 * @param pages IP ranges of GitHub Pages
 * @param importer IP ranges of the importer
 * @param actions IP ranges of GitHub Actions
 * @param actionsMacos IP ranges of GitHub Actions macOS runners
 * @param codespaces IP ranges of Codespaces
 * @param dependabot IP ranges of Dependabot
 * @param copilot IP ranges of Copilot
 * @param commitSigningKeys public keys that GitHub signs web commits with
 * @param domains domains that GitHub features use
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/api-overview")
public record GitHubApiOverview(
        boolean verifiablePasswordAuthentication,
        @Nullable GitHubSshKeyFingerprints sshKeyFingerprints,
        @Nullable List<String> sshKeys,
        @Nullable List<String> hooks,
        @Nullable List<String> githubEnterpriseImporter,
        @Nullable List<String> web,
        @Nullable List<String> api,
        @Nullable List<String> git,
        @Nullable List<String> packages,
        @Nullable List<String> pages,
        @Nullable List<String> importer,
        @Nullable List<String> actions,
        @Nullable List<String> actionsMacos,
        @Nullable List<String> codespaces,
        @Nullable List<String> dependabot,
        @Nullable List<String> copilot,
        @Nullable List<String> commitSigningKeys,
        @Nullable GitHubApiOverviewDomains domains,
        @Nullable String installedVersion) {}
