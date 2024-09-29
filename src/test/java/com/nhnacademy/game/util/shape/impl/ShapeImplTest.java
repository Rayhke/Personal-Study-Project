package com.nhnacademy.game.util.shape.impl;

import com.nhnacademy.game.error.InvalidSizeException;
import com.nhnacademy.game.error.OutOfBoundsException;
import com.nhnacademy.game.util.shape.Shape;
import com.nhnacademy.game.util.shape.impl.ShapeImpl.ShapeType;
import com.nhnacademy.game.util.world.World;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.UUID;
import java.util.stream.Stream;

import static com.nhnacademy.game.util.config.DefaultShape.DEFAULT_HEIGHT;
import static com.nhnacademy.game.util.config.DefaultShape.DEFAULT_MIN_X;
import static com.nhnacademy.game.util.config.DefaultShape.DEFAULT_MIN_Y;
import static com.nhnacademy.game.util.config.DefaultShape.DEFAULT_WIDTH;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShapeImplTest {

    private static final ShapeType DEFAULT_TYPE = ShapeType.BALL;

    private static final UUID DEFAULT_ID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "TestShape";

    private static final Rectangle DEFAULT_BOUNDS = new Rectangle(DEFAULT_MIN_X,
                                                                    DEFAULT_MIN_Y,
                                                                    DEFAULT_WIDTH,
                                                                    DEFAULT_HEIGHT);

    private static final World DEFAULT_WORLD = new World();

    // =================================================================================================================

    @DisplayName("생성자 테스트")
    @ParameterizedTest
    @MethodSource("constructorParameters")
    void constructorTest(Shape other, ShapeType shapeType, String name, Rectangle bounds) {
        assertDoesNotThrow(() -> {
            Shape shape = new ShapeImpl()
                                .shapeType(shapeType)
                                .id(other.getId())
                                .name(name)
                                .bounds(bounds);
            assertEquals(other.getShapeType(), shape.getShapeType());
            assertEquals(other.getId(), shape.getId());
            assertEquals(other.getName(), shape.getName());
            assertEquals(other.getMinX(), shape.getMinX());
            assertEquals(other.getMinY(), shape.getMinY());
            assertEquals(other.getWidth(), shape.getWidth());
            assertEquals(other.getHeight(), shape.getHeight());
            assertEquals(other.getMaxX(), shape.getMaxX());
            assertEquals(other.getMaxY(), shape.getMaxY());
            assertEquals(other.getCenterX(), shape.getCenterX());
            assertEquals(other.getCenterY(), shape.getCenterY());
        });
    }

    static Stream<Arguments> constructorParameters() {
        return Stream.of(
                Arguments.of(new ShapeImpl().name(DEFAULT_NAME),
                        DEFAULT_TYPE,
                        DEFAULT_NAME,
                        DEFAULT_BOUNDS),
                Arguments.of(new ShapeImpl()
                                .shapeType(ShapeType.BOX)
                                .id(DEFAULT_ID)
                                .name(DEFAULT_NAME)
                                .bounds(50, 50, 100, 100),
                        ShapeType.BOX,
                        DEFAULT_NAME,
                        new Rectangle(50, 50, 100, 100)),
                Arguments.of(new ShapeImpl()
                                .name("Hello World"),
                        DEFAULT_TYPE,
                        "Hello World",
                        DEFAULT_BOUNDS)
        );
    }

    // =================================================================================================================

    @DisplayName("생성자 예외 처리 테스트")
    @ParameterizedTest
    @MethodSource("constructorFailParameters")
    void constructorThrowsTest(ShapeType shapeType, UUID id, String name, Rectangle bounds, World world) {
        assertThrows(IllegalArgumentException.class, () -> new ShapeImpl()
                                                                .shapeType(shapeType)
                                                                .id(id)
                                                                .name(name)
                                                                .bounds(bounds)
                                                                .world(world));
    }

    static Stream<Arguments> constructorFailParameters() {
        return Stream.of(
                Arguments.of(null,
                        DEFAULT_ID,
                        DEFAULT_NAME,
                        DEFAULT_BOUNDS,
                        DEFAULT_WORLD),
                Arguments.of(DEFAULT_TYPE,
                        null,
                        DEFAULT_NAME,
                        DEFAULT_BOUNDS,
                        DEFAULT_WORLD),
                Arguments.of(DEFAULT_TYPE,
                        DEFAULT_ID,
                        null,
                        DEFAULT_BOUNDS,
                        DEFAULT_WORLD),
                Arguments.of(DEFAULT_TYPE,
                        DEFAULT_ID,
                        "",
                        DEFAULT_BOUNDS,
                        DEFAULT_WORLD),
                Arguments.of(DEFAULT_TYPE,
                        DEFAULT_ID,
                        "         ",
                        DEFAULT_BOUNDS,
                        DEFAULT_WORLD),
                Arguments.of(DEFAULT_TYPE,
                        DEFAULT_ID,
                        DEFAULT_NAME,
                        null,
                        DEFAULT_WORLD),
                Arguments.of(DEFAULT_TYPE,
                        DEFAULT_ID,
                        DEFAULT_NAME,
                        DEFAULT_BOUNDS,
                        null)
        );
    }

    // =================================================================================================================

    @DisplayName("메서드 null 예외 처리 테스트")
    @Test
    void methodThrowsTest() {
        Shape shape = new ShapeImpl();

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> shape.setName(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> shape.setName("")),
                () -> assertThrows(IllegalArgumentException.class, () -> shape.setName("  ")),
                () -> assertThrows(IllegalArgumentException.class, () -> shape.setLocation(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> shape.isCollision(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> shape.intersection(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> shape.setWorld(null))
        );
    }

    // =================================================================================================================

    @DisplayName("도형의 영역이 제한된 필드 영역을 벗어났을 때, 예외 처리 테스트")
    @ParameterizedTest
    @MethodSource("boundsFailParameters1")
    void boundsThrowsTest1(Rectangle bounds, int x, int y) {
        Shape shape = new ShapeImpl();
        assertThrows(OutOfBoundsException.class, () -> new ShapeImpl().bounds(bounds));
        assertThrows(OutOfBoundsException.class, () -> shape.setLocation(x, y));
    }

    static Stream<Arguments> boundsFailParameters1() {
        return Stream.of(
                Arguments.of(new Rectangle(0, DEFAULT_MIN_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT), 0, 1),
                Arguments.of(new Rectangle(DEFAULT_MIN_X, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT), 1, 0),
                Arguments.of(new Rectangle(-1, -1, DEFAULT_WIDTH, DEFAULT_HEIGHT), 0, 0)
        );
    }

    // =================================================================================================================

    @DisplayName("도형의 너비(폭), 높이의 예외 처리 테스트")
    @ParameterizedTest
    @MethodSource("boundsFailParameters2")
    void boundsThrowsTest2(int minX, int minY, int width, int height) {
        assertThrows(InvalidSizeException.class, () -> new ShapeImpl().bounds(minX, minY, width, height));
    }

    static Stream<Arguments> boundsFailParameters2() {
        return Stream.of(
                Arguments.of(DEFAULT_MIN_X, DEFAULT_MIN_Y, 0, 1),
                Arguments.of(DEFAULT_MIN_X, DEFAULT_MIN_Y, 1, 0),
                Arguments.of(DEFAULT_MIN_X, DEFAULT_MIN_Y, 0, 0),
                Arguments.of(DEFAULT_MIN_X, DEFAULT_MIN_Y, Integer.MIN_VALUE, Integer.MIN_VALUE)
        );
    }
}