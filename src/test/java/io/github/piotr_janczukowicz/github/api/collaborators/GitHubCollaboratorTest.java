package io.github.piotr_janczukowicz.github.api.collaborators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubCollaboratorTest {

    @ParameterizedTest
    @ValueSource(
            strings = {
                "login",
                "nodeId",
                "avatarUrl",
                "url",
                "htmlUrl",
                "followersUrl",
                "followingUrl",
                "gistsUrl",
                "starredUrl",
                "subscriptionsUrl",
                "organizationsUrl",
                "reposUrl",
                "eventsUrl",
                "receivedEventsUrl",
                "type"
            })
    void constructor_shouldThrow_whenRequiredNull(final String field) {
        final Throwable thrown = catchThrowable(() -> withNull(field));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(field);
    }

    private static GitHubCollaborator withNull(final String field) {
        final GitHubCollaborator base = CollaboratorsCatalogFixtures.collaborator();

        return new GitHubCollaborator(
                nullIf(field, "login", base.login()),
                base.id(),
                base.email(),
                base.name(),
                nullIf(field, "nodeId", base.nodeId()),
                nullIf(field, "avatarUrl", base.avatarUrl()),
                base.gravatarId(),
                nullIf(field, "url", base.url()),
                nullIf(field, "htmlUrl", base.htmlUrl()),
                nullIf(field, "followersUrl", base.followersUrl()),
                nullIf(field, "followingUrl", base.followingUrl()),
                nullIf(field, "gistsUrl", base.gistsUrl()),
                nullIf(field, "starredUrl", base.starredUrl()),
                nullIf(field, "subscriptionsUrl", base.subscriptionsUrl()),
                nullIf(field, "organizationsUrl", base.organizationsUrl()),
                nullIf(field, "reposUrl", base.reposUrl()),
                nullIf(field, "eventsUrl", base.eventsUrl()),
                nullIf(field, "receivedEventsUrl", base.receivedEventsUrl()),
                nullIf(field, "type", base.type()),
                base.siteAdmin(),
                base.permissions(),
                nullIf(field, "roleName", base.roleName()),
                base.userViewType());
    }

    private static <T> T nullIf(final String field, final String name, final T value) {
        if (name.equals(field)) {
            return null;
        }

        return value;
    }
}
