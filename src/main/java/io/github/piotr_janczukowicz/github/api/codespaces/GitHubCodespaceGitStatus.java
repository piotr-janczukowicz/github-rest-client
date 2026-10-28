package io.github.piotr_janczukowicz.github.api.codespaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Details about the codespace's git repository.
 *
 * @param ahead The number of commits the local repository is ahead of the remote.
 * @param behind The number of commits the local repository is behind the remote.
 * @param hasUnpushedChanges Whether the local repository has unpushed changes.
 * @param hasUncommittedChanges Whether the local repository has uncommitted changes.
 * @param ref The current branch (or SHA if in detached HEAD state) of the local repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/codespace/properties/git_status",
    "components/schemas/codespace-with-full-repository/properties/git_status"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespaceGitStatus(
        @Nullable Integer ahead,
        @Nullable Integer behind,
        @Nullable Boolean hasUnpushedChanges,
        @Nullable Boolean hasUncommittedChanges,
        @Nullable String ref) {}
