package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.lang.reflect.Constructor;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class SandboxGuardTest {

    @Test
    void requireSandbox_shouldPass_whenRepoNameContainsSmoke() {
        final SmokeEnvironment env = SmokeEnvironment.of(Map.of(SmokeEnvironment.REPO_VARIABLE, "acme/My-SMOKE-box"));

        final Throwable thrown = catchThrowable(() -> SandboxGuard.requireSandbox(env));

        assertThat(thrown).isNull();
    }

    @Test
    void requireSandbox_shouldThrow_whenRepoLooksLikeAProductionRepo() {
        final SmokeEnvironment env = SmokeEnvironment.of(Map.of(SmokeEnvironment.REPO_VARIABLE, "acme/github-api"));

        final Throwable thrown = catchThrowable(() -> SandboxGuard.requireSandbox(env));

        assertThat(thrown).isInstanceOf(IllegalStateException.class).hasMessageContaining("acme/github-api");
    }

    @Test
    void constructor_shouldBeUnsupported() throws ReflectiveOperationException {
        final Constructor<SandboxGuard> constructor = SandboxGuard.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        final Throwable thrown = catchThrowable(constructor::newInstance);

        assertThat(thrown).hasCauseInstanceOf(UnsupportedOperationException.class);
    }
}
