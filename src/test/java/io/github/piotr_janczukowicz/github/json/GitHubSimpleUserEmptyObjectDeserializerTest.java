package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;

@UnitTest
class GitHubSimpleUserEmptyObjectDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnNullAuthor_whenAuthorIsEmptyObject() {
        final byte[] json = FileUtils.readBytes(
                GitHubSimpleUserEmptyObjectDeserializerTest.class, "commit-author-empty-object.json");

        final GitHubCommit decoded = CODEC.decode(json, GitHubCommit.class);

        assertThat(decoded.author()).isNull();
        assertThat(decoded.committer())
                .isEqualTo(RepositoriesCatalogFixtures.commit().committer());
    }

    @Test
    void decode_shouldReturnNullCommitter_whenCommitterIsJsonNull() {
        final byte[] json =
                FileUtils.readBytes(GitHubSimpleUserEmptyObjectDeserializerTest.class, "commit-committer-null.json");

        final GitHubCommit decoded = CODEC.decode(json, GitHubCommit.class);

        assertThat(decoded.committer()).isNull();
        assertThat(decoded.author())
                .isEqualTo(RepositoriesCatalogFixtures.commit().author());
    }

    @Test
    void decode_shouldReturnUsers_whenAuthorAndCommitterAreSimpleUsers() {
        final byte[] json = FileUtils.readBytes(GitHubCommit.class, "commit.json");

        final GitHubCommit decoded = CODEC.decode(json, GitHubCommit.class);

        assertThat(decoded).isEqualTo(RepositoriesCatalogFixtures.commit());
    }

    @Test
    void decode_shouldThrow_whenAuthorIsNotObject() {
        final byte[] json =
                FileUtils.readBytes(GitHubSimpleUserEmptyObjectDeserializerTest.class, "commit-author-array.json");

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubCommit.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }

    @Test
    void getNullValue_shouldReturnNull_whenCalled() {
        assertThat(new GitHubSimpleUserEmptyObjectDeserializer().getNullValue(null))
                .isNull();
    }

    @Test
    void deserialize_shouldReturnNull_whenTokenIsNull() {
        final JsonParser parser = Mockito.mock(JsonParser.class);
        Mockito.when(parser.currentToken()).thenReturn(JsonToken.VALUE_NULL);

        assertThat(new GitHubSimpleUserEmptyObjectDeserializer().deserialize(parser, null))
                .isNull();
    }

    @Test
    void deserialize_shouldDelegateToContext_whenTokenIsNotObject() {
        final JsonParser parser = Mockito.mock(JsonParser.class);
        final DeserializationContext context = Mockito.mock(DeserializationContext.class);
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();
        Mockito.when(parser.currentToken()).thenReturn(JsonToken.VALUE_STRING);
        Mockito.when(context.readValue(parser, GitHubSimpleUser.class)).thenReturn(user);

        assertThat(new GitHubSimpleUserEmptyObjectDeserializer().deserialize(parser, context))
                .isSameAs(user);
    }
}
