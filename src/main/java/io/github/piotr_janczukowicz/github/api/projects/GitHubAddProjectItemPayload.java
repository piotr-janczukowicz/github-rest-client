package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Add project item request body.
 *
 * @param type Item type
 * @param id Issue or pull request id
 * @param owner Repository owner
 * @param repo Repository name
 * @param number Issue or pull request number
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"operations/projects/add-item-for-org/requestBody", "operations/projects/add-item-for-user/requestBody"
})
public record GitHubAddProjectItemPayload(
        GitHubProjectItemContentType type,
        @Nullable Long id,
        @Nullable String owner,
        @Nullable String repo,
        @Nullable Integer number) {

    private static final String PARAM_TYPE = "type";

    public GitHubAddProjectItemPayload {
        requireNonNull(type, PARAM_TYPE);
        if (type != GitHubProjectItemContentType.ISSUE && type != GitHubProjectItemContentType.PULL_REQUEST) {
            throw new IllegalArgumentException(PARAM_TYPE + " must be Issue or PullRequest");
        }

        final boolean hasId = id != null;
        final boolean hasRepository = owner != null || repo != null || number != null;

        if (hasId == hasRepository) {
            throw new IllegalArgumentException("exactly one of id or owner, repo, and number is required");
        }

        if (hasRepository) {
            requireNonNull(owner, "owner");
            requireNonNull(repo, "repo");
            requireNonNull(number, "number");
        }
    }

    /**
     * Adds an item by global id.
     *
     * @param type item type
     * @param id issue or pull request id
     * @return payload
     */
    public static GitHubAddProjectItemPayload byId(final GitHubProjectItemContentType type, final long id) {
        return new GitHubAddProjectItemPayload(type, id, null, null, null);
    }

    /**
     * Adds an item by repository coordinates.
     *
     * @param type item type
     * @param owner repository owner
     * @param repo repository name
     * @param number issue or pull request number
     * @return payload
     */
    public static GitHubAddProjectItemPayload byRepository(
            final GitHubProjectItemContentType type, final String owner, final String repo, final int number) {
        return new GitHubAddProjectItemPayload(type, null, owner, repo, number);
    }
}
