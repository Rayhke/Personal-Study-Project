package com.nhnacademy.game.util.move.impl;

import com.nhnacademy.game.util.move.Movable;
import com.nhnacademy.game.util.vector.Motion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.nhnacademy.game.util.config.DefaultMotion.DEFAULT_DX;
import static com.nhnacademy.game.util.config.DefaultMotion.DEFAULT_DY;
import static com.nhnacademy.game.util.config.DefaultMovable.DEFAULT_DT;
import static org.junit.jupiter.api.Assertions.*;

class MovableImplTest {

    private static final Motion DEFAULT_MOTION = new Motion(DEFAULT_DX, DEFAULT_DY);

    // =================================================================================================================

    @DisplayName("생성자 테스트")
    @ParameterizedTest
    @MethodSource("constructorParameters")
    void constructorTest(Movable other, Motion motion, int dt) {
        assertDoesNotThrow(() -> {
            Movable movable = new MovableImpl()
                                    .motion(motion)
                                    .dt(dt);
            assertEquals(other.getMotion().getDX(), movable.getMotion().getDX());
            assertEquals(other.getMotion().getDY(), movable.getMotion().getDY());
            assertEquals(other.getDT(), movable.getDT());
        });
    }

    static Stream<Arguments> constructorParameters() {
        return Stream.of(
                Arguments.of(new MovableImpl(),
                        DEFAULT_MOTION,
                        DEFAULT_DT)
        );
    }

    // =================================================================================================================


}