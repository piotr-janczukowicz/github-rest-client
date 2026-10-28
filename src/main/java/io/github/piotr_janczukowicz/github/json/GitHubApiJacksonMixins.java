package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.api.migrations.GitHubImport;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.search.GitHubLabelSearchResultItem;
import io.github.piotr_janczukowicz.github.api.search.GitHubTopicSearchResultItem;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationCommit;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocationWikiCommit;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.jackson.IntegralDoubleSerializer;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.module.SimpleModule;

/**
 * Jackson mixins owned by {@code json} so {@code api} models do not import adapters.
 *
 * <p>{@link IntegralDoubleSerializer} keeps GitHub {@code number} fields that hold whole values in the integer form the
 * API examples use.
 */
final class GitHubApiJacksonMixins {

    private GitHubApiJacksonMixins() {
        throw new UnsupportedOperationException("Utility class");
    }

    static void register(final SimpleModule module) {
        module.setMixInAnnotation(GitHubCommit.class, Commit.class);
        module.setMixInAnnotation(GitHubImport.class, Import.class);
        module.setMixInAnnotation(GitHubLabelSearchResultItem.class, SearchScore.class);
        module.setMixInAnnotation(GitHubTopicSearchResultItem.class, SearchScore.class);
        module.setMixInAnnotation(GitHubSecretScanningLocationCommit.class, LineColumns.class);
        module.setMixInAnnotation(GitHubSecretScanningLocationWikiCommit.class, LineColumns.class);
    }

    interface Commit {

        @JsonDeserialize(using = GitHubSimpleUserEmptyObjectDeserializer.class)
        GitHubSimpleUser author();

        @JsonDeserialize(using = GitHubSimpleUserEmptyObjectDeserializer.class)
        GitHubSimpleUser committer();
    }

    interface Import {

        @JsonDeserialize(using = GitHubImportUseLfsFlagDeserializer.class)
        Boolean useLfs();
    }

    interface SearchScore {

        @JsonSerialize(using = IntegralDoubleSerializer.class)
        double score();
    }

    interface LineColumns {

        @JsonSerialize(using = IntegralDoubleSerializer.class)
        double startLine();

        @JsonSerialize(using = IntegralDoubleSerializer.class)
        double endLine();

        @JsonSerialize(using = IntegralDoubleSerializer.class)
        double startColumn();

        @JsonSerialize(using = IntegralDoubleSerializer.class)
        double endColumn();
    }
}
