package com.nhnacademy.game.util.shape.impl;

import com.nhnacademy.game.error.InvalidSizeException;
import com.nhnacademy.game.error.OutOfBoundsException;
import com.nhnacademy.game.util.config.DefaultShape;
import com.nhnacademy.game.util.shape.Shape;
import com.nhnacademy.game.util.world.World;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;
import java.util.UUID;

/**
 * @version Java 11 이상
 */
public class ShapeImpl implements Shape {

    /**
     * 도형
     */
    public enum ShapeType {
        BALL, BOX
    }

    /**
     * 도형의 타입
     */
    private ShapeType shapeType;

    /**
     * 객체 ID
     */
    private UUID id;

    /**
     * 객체 이름
     */
    private String name;

    /**
     * 도형이 차지하는 영역
     */
    private Rectangle bounds;

    /**
     * 내가 속한 World 에 대한 정보를 참조할 목적으로 사용됨
     */
    protected World world;

    /**
     * Default Constructor
     */
    public ShapeImpl() {
        this.shapeType = ShapeType.BALL;
        this.id = UUID.randomUUID();
        this.name = id.toString();
        this.bounds = new Rectangle(DefaultShape.DEFAULT_MIN_X,
                                    DefaultShape.DEFAULT_MIN_Y,
                                    DefaultShape.DEFAULT_WIDTH,
                                    DefaultShape.DEFAULT_HEIGHT);
    }

    // =================================================================================================================

    public ShapeImpl shapeType(ShapeType shapeType) {
        if (Objects.isNull(shapeType)) {
            throw new IllegalArgumentException("shapeType is Null!");
        }
        this.shapeType = shapeType;
        return this;
    }

    public ShapeImpl id(UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id is Null!");
        }
        this.id = id;
        return this;
    }

    public ShapeImpl name(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("name is Null!");
        }
        this.name = name;
        return this;
    }

    public ShapeImpl bounds(Rectangle bounds) {
        if (Objects.isNull(bounds)) {
            throw new IllegalArgumentException("bounds is Null!");
        }
        return bounds((int) bounds.getMinX(),
                        (int) bounds.getMinY(),
                        (int) bounds.getWidth(),
                        (int) bounds.getHeight());
    }

    public ShapeImpl bounds(int minX, int minY, int width, int height) {
        boundsRangeCheck(minX, minY, width, height);
        this.bounds = new Rectangle(minX, minY, width, height);
        return this;
    }

    public ShapeImpl world(World world) {
        if (Objects.isNull(world)) {
            throw new IllegalArgumentException("world is Null!");
        }
        this.world = world;
        return this;
    }

    // =================================================================================================================

    @Override
    public ShapeType getShapeType() {
        return shapeType;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("name is Null!");
        }
        this.name = name;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public Point getLocation() {
        return bounds.getLocation();
    }

    @Override
    public void setLocation(Point location) {
        if (Objects.isNull(location)) {
            throw new IllegalArgumentException("location is Null!");
        }
        bounds.setLocation(location);
    }

    @Override
    public void setLocation(int x, int y) {
        boundsRangeCheck(x, y, getWidth(), getHeight());
        bounds.setLocation(x, y);
    }

    @Override
    public int getCenterX() {
        return (int) bounds.getCenterX();
    }

    @Override
    public int getCenterY() {
        return (int) bounds.getCenterY();
    }

    @Override
    public int getMinX() {
        return (int) bounds.getMinX();
    }

    @Override
    public int getMaxX() {
        return (int) bounds.getMaxX();
    }

    @Override
    public int getMinY() {
        return (int) bounds.getMinY();
    }

    @Override
    public int getMaxY() {
        return (int) bounds.getMaxY();
    }

    @Override
    public int getWidth() {
        return (int) bounds.getWidth();
    }

    @Override
    public int getHeight() {
        return (int) bounds.getHeight();
    }

    @Override
    public boolean isCollision(Shape shape) {
        if (Objects.isNull(shape)) {
            throw new IllegalArgumentException("shape is Null!");
        }
        return bounds.intersects(shape.getBounds());
    }

    @Override
    public Rectangle intersection(Shape shape) {
        if (Objects.isNull(shape)) {
            throw new IllegalArgumentException("shape is Null!");
        }
        return bounds.intersection(shape.getBounds());
    }

    @Override
    public void setWorld(World world) {
        if (Objects.isNull(world)) {
            throw new IllegalArgumentException("world is Null!");
        }
        this.world = world;
    }

    @Override
    public String toString() {
        return String.format("%s[location(x=%d, y=%d), bounds(minX=%d, minY=%d, maxX=%d, maxY=%d, width=%d, height=%d)]",
                getShapeType(), getCenterX(), getCenterY(), getMinX(), getMinY(), getMaxX(), getMaxY(), getWidth(), getHeight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShapeImpl shape = (ShapeImpl) o;
        return shapeType == shape.shapeType
                && Objects.equals(id, shape.id)
                && Objects.equals(name, shape.name)
                && Objects.equals(bounds, shape.bounds)
                && Objects.equals(world, shape.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shapeType, id, name, bounds, world);
    }

    // =================================================================================================================

    private void boundsRangeCheck(int minX, int minY, int width, int height) {
        if (width < 1 || height < 1) {
            throw new InvalidSizeException(width, height);
        }

        if (minX < 1 || Integer.MAX_VALUE == minX + width
                || minY < 1 || Integer.MAX_VALUE == minY + height) {
            throw new OutOfBoundsException();
        }
    }
}
