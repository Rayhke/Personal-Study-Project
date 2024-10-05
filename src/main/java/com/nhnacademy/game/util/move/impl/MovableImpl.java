package com.nhnacademy.game.util.move.impl;

import com.nhnacademy.game.error.InvalidSizeException;
import com.nhnacademy.game.error.OutOfBoundsException;
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

    /**
     * TODO: 당장 쓸지는 미지수
     */
    private int moveCount;

    public MovableImpl() {
        super();
        this.motion = new Motion();
        this.dt = DefaultMovable.DEFAULT_DT;
    }

    /**
     * Subclass Constructor
     *
     * @param shapeType 도형의 타입
     * @param id        도형의 고유 ID
     * @param name      도형에게 지정할 이름
     * @param minX      도형이 차지하는 영역 minX
     * @param minY      도형의 차지하는 영역 minY
     * @param width     도형이 차지하는 영역 너비(폭)
     * @param height    도형이 차지하는 영역 높이
     * @param color     도형의 색상
     * @param motion    도형의 이동 변위량
     * @param dt        도형의 이동 딜레이 (ms 단위)
     * @throws IllegalArgumentException 입력된 파라미터 중에 null 이 있는 경우
     * @throws InvalidSizeException     도형이 차지하는 영역의 넓이가 1미만일 경우
     * @throws OutOfBoundsException     도형의 차지하는 영역이 정수 범위 내의 공간을 벗어날 경우
     * @deprecated
     */
    protected MovableImpl(ShapeType shapeType, UUID id, String name, int minX, int minY, int width, int height, Color color, Motion motion, int dt) {
        super(shapeType, id, name, minX, minY, width, height, color);

        if (Objects.isNull(motion)) {
            throw new IllegalArgumentException("motion is Null!");
        }

        if (dt < 0) {
            throw new IllegalArgumentException();
        }

        this.motion = motion;
        this.dt = dt;
        this.moveCount = 0;
    }

    // =================================================================================================================
    // builder

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
        super.bounds(bounds);
        return this;
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
    // thread

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
        // 충돌이 발생했을 시, 움직이도록
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
    // method

    @Override
    public Motion getMotion() {
        return motion;
    }

    @Override
    public void setMotion(Motion motion) {
        if (Objects.isNull(motion)) {
            throw new IllegalArgumentException("motion is Null!");
        }
        this.motion.setMotion(motion.getDX(), motion.getDY());
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
        //TODO: log 감시
        log.debug("minX = {}, minY = {}, maxX = {}, maxY = {}",
                x, y, x + super.getWidth(), y + super.getHeight());
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

    public void addMoveCount() {
        moveCount++;
    }

    @Override
    public String toString() {
        return String.format("[%s]\t\t - %s[Location(x=%d, y=%d), Bounds(minX=%d, minY=%d, maxX=%d, maxY=%d, width=%d, height=%d), Motion(dx=%d, dy=%d), Color=%s, dt=%d]",
                getId(), getShapeType(), getCenterX(), getCenterY(), getMinX(), getMinY(), getMaxX(), getMaxY(), getWidth(), getHeight(),
                getMotion().getDX(), getMotion().getDY(), getColor(), getDT());
    }
}
