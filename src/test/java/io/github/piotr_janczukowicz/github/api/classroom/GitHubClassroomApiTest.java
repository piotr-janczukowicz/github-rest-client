package io.github.piotr_janczukowicz.github.api.classroom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
@SuppressWarnings("deprecation")
class GitHubClassroomApiTest {

    private static final int ID = 42;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubClassroomApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getAssignment_shouldThrowUnsupportedOperation_whenCalled() {
        final GitHubClassroomApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getAssignment(ID));

        assertRemoved(thrown);
    }

    @Test
    void listAcceptedAssignmentsForAssignment_shouldThrowUnsupportedOperation_whenCalled() {
        final GitHubClassroomApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listAcceptedAssignmentsForAssignment(ID));

        assertRemoved(thrown);
    }

    @Test
    void getAssignmentGrades_shouldThrowUnsupportedOperation_whenCalled() {
        final GitHubClassroomApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getAssignmentGrades(ID));

        assertRemoved(thrown);
    }

    @Test
    void listClassrooms_shouldThrowUnsupportedOperation_whenCalled() {
        final GitHubClassroomApi api = api();

        final Throwable thrown = catchThrowable(api::listClassrooms);

        assertRemoved(thrown);
    }

    @Test
    void getClassroom_shouldThrowUnsupportedOperation_whenCalled() {
        final GitHubClassroomApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getClassroom(ID));

        assertRemoved(thrown);
    }

    @Test
    void listAssignmentsForClassroom_shouldThrowUnsupportedOperation_whenCalled() {
        final GitHubClassroomApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listAssignmentsForClassroom(ID));

        assertRemoved(thrown);
    }

    private GitHubClassroomApi api() {
        return new GitHubClassroomApi(TestClients.caller(transport));
    }

    private static void assertRemoved(final Throwable thrown) {
        assertSoftly(softly -> {
            softly.assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
            softly.assertThat(thrown)
                    .hasMessageContaining("Classroom API has been removed")
                    .hasMessageContaining("2026-08-28")
                    .hasMessageContaining("https://gh.io/classroom-sunset");
        });
    }
}
