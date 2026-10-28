package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubVerification;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Metadata for a Git tag.
 *
 * @param nodeId Node id
 * @param tag Name of the tag
 * @param sha Sha
 * @param url URL for the tag
 * @param message Message describing the purpose of the tag
 * @param tagger Tagger
 * @param object Object
 * @param verification Verification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-tag")
public record GitHubGitTag(
        String nodeId,
        String tag,
        String sha,
        String url,
        String message,
        GitHubGitTagTagger tagger,
        GitHubGitTagObject object,
        @Nullable GitHubVerification verification) {

    public GitHubGitTag {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(tag, "tag");
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
        requireNonNull(message, "message");
        requireNonNull(tagger, "tagger");
        requireNonNull(object, "object");
    }
}
