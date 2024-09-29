package com.nhnacademy.game.util.move.impl;

import com.nhnacademy.game.error.OutOfTimeRangeException;
import com.nhnacademy.game.util.move.Movable;
import com.nhnacademy.game.util.vector.Motion;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.Random;
import java.util.stream.Stream;

import static com.nhnacademy.game.util.config.DefaultMotion.DEFAULT_DX;
import static com.nhnacademy.game.util.config.DefaultMotion.DEFAULT_DY;
import static com.nhnacademy.game.util.config.DefaultMovable.DEFAULT_DT;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MovableImplTest {

    private static final Random RANDOM = new Random();

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

    @DisplayName("생성자 예외 처리 테스트")
    @Test
    void constructorThrowsTest() {
        assertThrows(IllegalArgumentException.class, () -> new MovableImpl()
                                                                .motion(null));
    }

    // =================================================================================================================

    @DisplayName("메서드 예외 처리 테스트")
    @RepeatedTest(100)
    void methodThrowsTest(RepetitionInfo info) {
        Movable movable = new MovableImpl();
        int dt = RANDOM.nextInt(Integer.MAX_VALUE) * -1;

        log.debug("===================");
        log.debug("[{} / {}]", info.getCurrentRepetition(), info.getTotalRepetitions());
        log.debug("DT : {}", dt);
        log.debug("===================");
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> movable.setMotion(null)),
                () -> assertThrows(OutOfTimeRangeException.class, () -> movable.setDT(dt))
        );
    }

    // =================================================================================================================

    @DisplayName("좌표 이동 테스트")
    @RepeatedTest(100)
    void moveLocationTest(RepetitionInfo info) {
        int minX = RANDOM.nextInt(Integer.MAX_VALUE - 10_00);
        int minY = RANDOM.nextInt(Integer.MAX_VALUE - 10_00);
        int dx = RANDOM.nextInt(Integer.MAX_VALUE - 10_00 - minX);
        int dy = RANDOM.nextInt(Integer.MAX_VALUE - 10_00 - minY);
        Point answer = new Point(minX + dx, minY + dy);

        Movable movable = new MovableImpl()
                                .motion(dx, dy);
        movable.setLocation(minX, minY);

        log.debug("===================");
        log.debug("[{} / {}]", info.getCurrentRepetition(), info.getTotalRepetitions());
        log.debug("[좌표 이동 변위량] :\t {}", movable.getMotion());

        log.debug("[이동 전] :\t\t minX={},\t minY={}", movable.getMinX(), movable.getMinY());
        movable.move();
        log.debug("[이동 후] :\t\t minX={},\t minY={}", movable.getMinX(), movable.getMinY());
        log.debug("===================");

        assertAll(
                () -> assertEquals(answer.getX(), movable.getMinX()),
                () -> assertEquals(answer.getY(), movable.getMinY())
        );
    }
}