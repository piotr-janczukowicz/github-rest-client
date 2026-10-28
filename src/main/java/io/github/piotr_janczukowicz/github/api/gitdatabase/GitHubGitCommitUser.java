package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Identifying information for the git-user.
 *
 * @param date Timestamp of the commit
 * @param email Git email address of the user
 * @param name Name of the git user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"components/schemas/git-commit/properties/author", "components/schemas/git-commit/properties/committer"
})
public record GitHubGitCommitUser(Instant date, String email, String name) {

    public GitHubGitCommitUser {
        requireNonNull(date, "date");
        requireNonNull(email, "email");
        requireNonNull(name, "name");
    }
}
