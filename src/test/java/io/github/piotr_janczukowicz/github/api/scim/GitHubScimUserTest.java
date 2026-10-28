package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimUser user = ScimCatalogFixtures.scimUser();

        assertThat(user.id()).isEqualTo("edefdfedf-050c-11e7-8d32");
        assertThat(user.active()).isTrue();
        assertThat(user.organizationId()).isEqualTo(1L);
        assertThat(user.operations()).containsExactly(ScimCatalogFixtures.scimUserOperation());
    }

    @Test
    void constructor_shouldKeepAbsentLists_whenOptionalListsNull() {
        final GitHubScimUser user = new GitHubScimUser(
                List.of("urn:ietf:params:scim:schemas:core:2.0:User"),
                "edefdfedf-050c-11e7-8d32",
                null,
                null,
                null,
                null,
                null,
                List.of(ScimCatalogFixtures.scimUserEmail()),
                true,
                ScimCatalogFixtures.scimUserMeta(),
                null,
                null,
                null,
                null);

        assertThat(user.operations()).isNull();
        assertThat(user.groups()).isNull();
        assertThat(user.roles()).isNull();
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEmailsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubScimUser(
                List.of("urn:ietf:params:scim:schemas:core:2.0:User"),
                "edefdfedf-050c-11e7-8d32",
                null,
                null,
                null,
                null,
                null,
                null,
                true,
                ScimCatalogFixtures.scimUserMeta(),
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("emails");
    }
}
