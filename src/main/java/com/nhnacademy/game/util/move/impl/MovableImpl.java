package com.nhnacademy.game.util.move.impl;

import com.nhnacademy.game.error.OutOfTimeRangeException;
import com.nhnacademy.game.util.config.DefaultMovable;
import com.nhnacademy.game.util.move.Movable;
import com.nhnacademy.game.util.paint.impl.PaintableImpl;
import com.nhnacademy.game.util.vector.Motion;
import com.nhnacademy.game.util.world.World;
import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class MovableImpl extends PaintableImpl implements Movable {

    private Motion motion;

    private int dt;

    public MovableImpl() {
        super();
        this.motion = new Motion();
        this.dt = DefaultMovable.DEFAULT_DT;
    }

    // =================================================================================================================

    @Override
    public MovableImpl shapeType(ShapeType shapeType) {
        super.shapeType(shapeType);
        return this;
    }

    @Override
    public MovableImpl id(UUID id) {
        super.id(id);
        return this;
    }

    @Override
    public MovableImpl name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public MovableImpl bounds(Rectangle bounds) {
        return bounds((int) bounds.getMinX(),
                        (int) bounds.getMinY(),
                        (int) bounds.getWidth(),
                        (int) bounds.getHeight());
    }

    @Override
    public MovableImpl bounds(int minX, int minY, int width, int height) {
        super.bounds(minX, minY, width, height);
        return this;
    }

    @Override
    public MovableImpl world(World world) {
        super.world(world);
        return this;
    }

    @Override
    public MovableImpl color(Color color) {
        super.color(color);
        return this;
    }

    public MovableImpl motion(Motion motion) {
        if (Objects.isNull(motion)) {
            throw new IllegalArgumentException("motion is Null!");
        }
        return motion(motion.getDX(), motion.getDY());
    }

    public MovableImpl motion(int dx, int dy) {
        this.motion.setMotion(dx, dy);
        return this;
    }

    public MovableImpl dt(int dt) {
        if (dt < 0) {
            throw new OutOfTimeRangeException(dt);
        }
        this.dt = dt;
        return this;
    }

    // =================================================================================================================

    @Override
    public void start() {
        Thread.currentThread().start();
    }

    @Override
    public void stop() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(dt);
                move();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("{}", e.getMessage(), e);
            }
        }
    }

    // =================================================================================================================

    @Override
    public Motion getMotion() {
        return motion;
    }

    @Override
    public void setMotion(Motion motion) {
        if (Objects.isNull(motion)) {
            throw new IllegalArgumentException("motion is Null!");
        }
        this.motion.setMotion(motion);
    }

    @Override
    public void setMotion(int dx, int dy) {
        motion.setMotion(dx, dy);
    }

    @Override
    public void setMotionToDX(int dx) {
        setMotion(dx, getMotion().getDY());
    }

    @Override
    public void setMotionToDY(int dy) {
        setMotion(getMotion().getDX(), dy);
    }

    @Override
    public synchronized void move() {
        moveTo((int) getBounds().getMinX() + getMotion().getDX(),
                (int) getBounds().getMinY() + getMotion().getDY());
    }

    /**
     * 도형을 지정된 위치로 이동 시킨다.
     *
     * @param x X축 상의 위치
     * @param y Y축 상의 위치
     */
    @Override
    public synchronized void moveTo(int x, int y) {
        /*int widthDiv2 = super.getWidth() / 2;
        int heightDiv2 = super.getHeight() / 2;
        log.debug("minX={}, minY={}, maxX={}, maxY={}",
                x - widthDiv2, y - heightDiv2, x + widthDiv2, y + heightDiv2);*/
        super.setLocation(x, y);
    }

    @Override
    public int getDT() {
        return dt;
    }

    @Override
    public void setDT(int dt) {
        if (dt < 0) {
            throw new OutOfTimeRangeException(dt);
        }
        this.dt = dt;
    }

    // TODO : 임시
    @Override
    public String toString() {
        return super.toString();
    }
}
