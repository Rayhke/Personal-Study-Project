package com.nhnacademy.game.util.paint.impl;

import com.nhnacademy.game.util.config.DefaultPaintable;
import com.nhnacademy.game.util.paint.Paintable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

import static com.nhnacademy.game.util.config.DefaultPaintable.DEFAULT_COLOR;
import static org.junit.jupiter.api.Assertions.*;

class PaintableImplTest {

    // =================================================================================================================

    @DisplayName("생성자 테스트")
    @ParameterizedTest
    @MethodSource("constructorParameters")
    void constructorTest(Paintable other, Color color) {
        assertDoesNotThrow(() -> {
            Paintable paintable = new PaintableImpl()
                                        .color(color);
            assertEquals(other.getColor(), paintable.getColor());
        });
    }


    static Stream<Arguments> constructorParameters() {
        return Stream.of(
                Arguments.of(new PaintableImpl(), DEFAULT_COLOR),
                Arguments.of(new PaintableImpl().color(Color.GRAY), Color.GRAY)
        );
    }

    // =================================================================================================================

    @DisplayName("생성자 예외 처리 테스트")
    @Test
    void constructorThrowsTest() {
        Paintable paintable = new PaintableImpl();

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new PaintableImpl().color(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> paintable.setColor(null))
        );
    }

    // =================================================================================================================

    @DisplayName("메서드 null 예외 처리 테스트")
    @Test
    void methodThrowsTest() {
        Paintable paintable = new PaintableImpl();
        assertThrows(NullPointerException.class, () -> paintable.paint(null));
    }
}