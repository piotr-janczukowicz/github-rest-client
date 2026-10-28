package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class IssueNumbersTest {

    @Test
    void wireDecimal_shouldDropTheFraction_whenValueIsWhole() {
        final BigDecimal decimal = IssueNumbers.wireDecimal(42.0d);

        assertThat(decimal).hasToString("42");
    }

    @Test
    void wireDecimal_shouldKeepTheFraction_whenValueIsNotWhole() {
        final BigDecimal decimal = IssueNumbers.wireDecimal(1.5d);

        assertThat(decimal).hasToString("1.5");
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void wireDecimal_shouldThrow_whenValueIsNotFinite(final double value) {
        final Throwable thrown = catchThrowable(() -> IssueNumbers.wireDecimal(value));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("value must be finite");
    }

    @Test
    void constructor_shouldRejectInstantiation_whenCalledViaReflection() throws Exception {
        final Constructor<IssueNumbers> constructor = IssueNumbers.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        final Throwable thrown = catchThrowable(constructor::newInstance);

        assertThat(thrown.getCause()).isInstanceOf(UnsupportedOperationException.class);
    }
}
