package io.github.piotr_janczukowicz.github.smoke;

import io.github.piotr_janczukowicz.github.api.GitHub;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsVariable;
import io.github.piotr_janczukowicz.github.api.deploykeys.GitHubDeployKey;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubGitRef;
import io.github.piotr_janczukowicz.github.api.issues.GitHubLabel;
import io.github.piotr_janczukowicz.github.api.releases.GitHubRelease;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
import java.io.IOException;
import java.util.Locale;

final class SmokeCleanup {

    private static final String PREFIX = "smoke-";

    private SmokeCleanup() {
        throw new UnsupportedOperationException("Utility class");
    }

    static void sweep(final SmokeEnvironment env) throws IOException {
        SandboxGuard.requireSandbox(env);
        final GitHub github = env.github();
        final String owner = env.owner();
        final String repo = env.repo();
        final String current = env.name("");
        for (final GitHubLabel label :
                github.issues().labels().listLabelsForRepo(owner, repo).all()) {
            if (isLeftover(label.name(), current)) {
                github.issues().labels().deleteLabel(owner, repo, label.name());
            }
        }
        for (final GitHubMilestone milestone :
                github.issues().milestones().listMilestones(owner, repo).all()) {
            if (isLeftover(milestone.title(), current)) {
                github.issues().milestones().deleteMilestone(owner, repo, milestone.number());
            }
        }
        for (final GitHubGitRef ref : github.gitDatabase()
                .refs()
                .listMatchingRefs(owner, repo, "heads/" + PREFIX)
                .all()) {
            if (isLeftover(ref.ref().substring("refs/heads/".length()), current)) {
                github.gitDatabase().refs().deleteRef(owner, repo, ref.ref().substring("refs/".length()));
            }
        }
        for (final GitHubRelease release :
                github.releases().release().listReleases(owner, repo).all()) {
            if (isLeftover(release.tagName(), current)) {
                github.releases().release().deleteRelease(owner, repo, release.id());
            }
        }
        for (final GitHubDeployKey key :
                github.deployKeys().listDeployKeys(owner, repo).all()) {
            if (isLeftover(key.title(), current)) {
                github.deployKeys().deleteDeployKey(owner, repo, key.id());
            }
        }
        for (final GitHubActionsVariable variable :
                github.actions().variables().listRepoVariables(owner, repo).all()) {
            if (variable.name().startsWith("SMOKE_")
                    && !variable.name().startsWith("SMOKE_" + env.runId().toUpperCase(Locale.ROOT))) {
                github.actions().variables().deleteRepoVariable(owner, repo, variable.name());
            }
        }
    }

    private static boolean isLeftover(final String name, final String current) {
        return name.startsWith(PREFIX) && !name.startsWith(current);
    }
}
