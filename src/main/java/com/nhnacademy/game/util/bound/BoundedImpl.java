package com.nhnacademy.game.util.bound;

import com.nhnacademy.game.util.config.DefaultBounded;
import com.nhnacademy.game.util.move.impl.MovableImpl;
import com.nhnacademy.game.util.shape.Shape;
import com.nhnacademy.game.util.vector.Motion;
import com.nhnacademy.game.util.world.World;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class BoundedImpl extends MovableImpl implements Bounded {

    /**
     * 도형이 접근할 수 있는 경계 영역
     */
    private Rectangle boundedArea;

    public BoundedImpl() {
        super();
        this.boundedArea = new Rectangle(DefaultBounded.DEFAULT_AREA_MIN_X,
                                            DefaultBounded.DEFAULT_AREA_MIN_Y,
                                            DefaultBounded.DEFAULT_AREA_WIDTH,
                                            DefaultBounded.DEFAULT_AREA_HEIGHT);
    }

    // =================================================================================================================
    // Thread

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            this.move();
        }
    }

    // =================================================================================================================
    // builder

    @Override
    public BoundedImpl shapeType(ShapeType shapeType) {
        super.shapeType(shapeType);
        return this;
    }

    @Override
    public BoundedImpl id(UUID id) {
        super.id(id);
        return this;
    }

    @Override
    public BoundedImpl name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public BoundedImpl bounds(Rectangle bounds) {
        super.bounds(bounds);
        return this;
    }

    @Override
    public BoundedImpl bounds(int minX, int minY, int width, int height) {
        super.bounds(minX, minY, width, height);
        return this;
    }

    @Override
    public BoundedImpl world(World world) {
        super.world(world);
        return this;
    }

    @Override
    public BoundedImpl color(Color color) {
        super.color(color);
        return this;
    }

    @Override
    public BoundedImpl motion(Motion motion) {
        super.motion(motion);
        return this;
    }

    @Override
    public BoundedImpl motion(int dx, int dy) {
        super.motion(dx, dy);
        return this;
    }

    @Override
    public BoundedImpl dt(int dt) {
        super.dt(dt);
        return this;
    }

    public BoundedImpl boundedArea(Rectangle boundedArea) {
        if (Objects.isNull(boundedArea)) {
            throw new IllegalArgumentException("boundedArea is Null!");
        }
        return boundedArea((int) boundedArea.getMinX(),
                            (int) boundedArea.getMinY(),
                            (int) boundedArea.getWidth(),
                            (int) boundedArea.getHeight());
    }

    public BoundedImpl boundedArea(int minX, int minY, int width, int height) {
        this.boundedArea = new Rectangle(minX, minY, width, height);
        return this;
    }

    // =================================================================================================================
    // method

    @Override
    public Rectangle getBoundedArea() {
        return boundedArea;
    }

    @Override
    public void setBoundedArea(Rectangle boundedArea) {
        if (Objects.isNull(boundedArea)) {
            throw new NullPointerException();
        }

        if (super.getMinX() < boundedArea.getMinX()
                || boundedArea.getWidth() < super.getWidth()
                || super.getMinY() < boundedArea.getMinY()
                || boundedArea.getHeight() < super.getHeight()) {
            throw new IllegalArgumentException();
        }

        this.boundedArea = new Rectangle(boundedArea);
    }

    @Override
    public Point getAreaLocation() {
        return boundedArea.getLocation();
    }

    @Override
    public int getAreaMinX() {
        return (int) boundedArea.getMinX();
    }

    @Override
    public int getAreaMaxX() {
        return (int) boundedArea.getMaxX();
    }

    @Override
    public int getAreaMinY() {
        return (int) boundedArea.getMinY();
    }

    @Override
    public int getAreaMaxY() {
        return (int) boundedArea.getMaxY();
    }

    @Override
    public int getAreaWidth() {
        return (int) boundedArea.getWidth();
    }

    @Override
    public int getAreaHeight() {
        return (int) boundedArea.getHeight();
    }

    @Override
    public boolean isOutOfBounds(Rectangle otherBounds) {
        return !getBoundedArea().intersects(otherBounds);
    }

    @Override
    public void bounce(Rectangle otherBounds) {
        if (isOutOfBounds(otherBounds)) {
            this.move(); return;
        }
        super.setLocation(otherBounds.getLocation());
    }

    /**
     * 이동 횟수를 제한하는 건 임시 보류
     */
    @Override
    public void move() {
        int x2 = super.getMinX() + super.getMotion().getDX();
        int y2 = super.getMinY() + super.getMotion().getDY();
        int x3 = x2;
        int y3 = y2;

        // Area == JFrame 의 Rectangle
        if (isOutOfBounds(super.getBounds())) {
            if (super.getMinX() < this.getAreaMinX()) {
                x3 = (super.getMinX() * 2) - x2 + 2;
                super.getMotion().turnDX();
            } else if (this.getAreaMaxX() < super.getMaxX()) {
                x3 = (super.getMinX() * 2) - x2;
                super.getMotion().turnDX();
            }

            if (super.getMinY() < this.getAreaMinY()) {
                y3 = (super.getMinY() * 2) - y2 + 2;
                super.getMotion().turnDY();
            } else if (this.getAreaMaxY() < super.getMaxY()) {
                y3 = (super.getMinY() * 2) - y2;
                super.getMotion().turnDY();
            }
        }

        try {
            Thread.sleep(super.getDT());
            super.moveTo(x3, y3);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("{}", e.getMessage(), e);
        }

        Shape otherShape;
        for (int n = 0; n < world.getShapeCount(); n++) {
            otherShape = world.getShape(n);
            if (otherShape.getId().equals(super.getId())) { continue; }

            if (super.isCollision(otherShape)) {
                Rectangle intersection = super.intersection(otherShape);

                if (intersection.getWidth() != super.getWidth()
                        && intersection.getWidth() != otherShape.getWidth()) {
                    getMotion().turnDX();
                }

                if (intersection.getHeight() != super.getHeight()
                        && intersection.getHeight() != otherShape.getHeight()) {
                    getMotion().turnDY();
                }
            }
        }
        super.addMoveCount();
    }
}
