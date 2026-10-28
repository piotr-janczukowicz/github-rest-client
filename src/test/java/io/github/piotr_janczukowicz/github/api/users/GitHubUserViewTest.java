package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

@UnitTest
class GitHubUserViewTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    private static final JsonMapper TREE = JsonMapper.builder().build();

    @Test
    void decode_shouldReturnPublicUser_whenPublicFixture() {
        final byte[] json = jsonBytes("public-user-octocat.json");

        final GitHubUserView userView = CODEC.decode(json, GitHubUserView.class);

        assertSoftly(softly -> {
            softly.assertThat(userView).isInstanceOf(GitHubUser.class);
            softly.assertThat(((GitHubUser) userView).userViewType()).isEqualTo(GitHubUserViewType.PUBLIC);
        });
    }

    @Test
    void decode_shouldReturnAuthenticatedUser_whenPrivateFixture() {
        final byte[] json = jsonBytes("private-user-octocat.json");

        final GitHubUserView userView = CODEC.decode(json, GitHubUserView.class);

        assertSoftly(softly -> {
            softly.assertThat(userView).isInstanceOf(GitHubAuthenticatedUser.class);
            softly.assertThat(((GitHubAuthenticatedUser) userView).userViewType())
                    .isEqualTo(GitHubUserViewType.PRIVATE);
        });
    }

    @Test
    void decode_shouldReturnPublicUser_whenUserViewTypeMissingOnPublicShape() {
        final JsonNode tree = TREE.readTree(jsonBytes("public-user-octocat.json"));
        ((ObjectNode) tree).remove("user_view_type");
        final byte[] json = TREE.writeValueAsBytes(tree);

        final GitHubUserView userView = CODEC.decode(json, GitHubUserView.class);

        assertSoftly(softly -> {
            softly.assertThat(userView).isInstanceOf(GitHubUser.class);
            softly.assertThat(((GitHubUser) userView).userViewType()).isEqualTo(GitHubUserViewType.PUBLIC);
        });
    }

    @Test
    void decode_shouldReturnAuthenticatedUser_whenUserViewTypeMissingOnPrivateShape() {
        final JsonNode tree = TREE.readTree(jsonBytes("private-user-octocat.json"));
        ((ObjectNode) tree).remove("user_view_type");
        final byte[] json = TREE.writeValueAsBytes(tree);

        final GitHubUserView userView = CODEC.decode(json, GitHubUserView.class);

        assertSoftly(softly -> {
            softly.assertThat(userView).isInstanceOf(GitHubAuthenticatedUser.class);
            softly.assertThat(((GitHubAuthenticatedUser) userView).userViewType())
                    .isEqualTo(GitHubUserViewType.PRIVATE);
        });
    }

    @Test
    void decode_shouldReturnPublicUser_whenUserViewTypeJsonNullOnPublicShape() {
        final JsonNode tree = TREE.readTree(jsonBytes("public-user-octocat.json"));
        ((ObjectNode) tree).putNull("user_view_type");
        final byte[] json = TREE.writeValueAsBytes(tree);

        final GitHubUserView userView = CODEC.decode(json, GitHubUserView.class);

        assertSoftly(softly -> {
            softly.assertThat(userView).isInstanceOf(GitHubUser.class);
            softly.assertThat(((GitHubUser) userView).userViewType()).isEqualTo(GitHubUserViewType.PUBLIC);
        });
    }

    @Test
    void decode_shouldReturnAuthenticatedUser_whenUserViewTypeBlankOnPrivateShape() {
        final JsonNode tree = TREE.readTree(jsonBytes("private-user-octocat.json"));
        ((ObjectNode) tree).put("user_view_type", "  ");
        final byte[] json = TREE.writeValueAsBytes(tree);

        final GitHubUserView userView = CODEC.decode(json, GitHubUserView.class);

        assertSoftly(softly -> {
            softly.assertThat(userView).isInstanceOf(GitHubAuthenticatedUser.class);
            softly.assertThat(((GitHubAuthenticatedUser) userView).userViewType())
                    .isEqualTo(GitHubUserViewType.PRIVATE);
        });
    }

    @Test
    void decode_shouldThrow_whenUserViewTypeUnknown() {
        final JsonNode tree = TREE.readTree(jsonBytes("public-user-octocat.json"));
        ((ObjectNode) tree).put("user_view_type", "bot");
        final byte[] json = TREE.writeValueAsBytes(tree);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubUserView.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("Unknown GitHub user view type")
                .hasMessageContaining("bot");
    }

    private static byte[] jsonBytes(final String fileName) {
        return FileUtils.readBytes(GitHubUserViewTest.class, fileName);
    }
}
