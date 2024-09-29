package com.nhnacademy.game.util.vector;

import com.nhnacademy.game.util.world.World;

public abstract class Vector {

    /**
     * X축 변위량
     */
    protected int dx;

    /**
     * Y축 변위량
     */
    protected int dy;

    /**
     * 벡터를 생성한다. 단, 확장 클래스에서만 사용하도록 한다.
     *
     * @param dx X축 변위량
     * @param dy Y축 변위량
     */
    protected Vector(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * 주어진 벡터를 더한다.
     *
     * @param other 피연산자
     */
    public void add(Vector other) {
        this.dx += other.getDX();
        this.dy += other.getDY();
    }

    /**
     * 주어진 벡터를 뺀다.
     *
     * @param other 피연산자
     */
    public void sub(Vector other) {
        this.dx -= other.getDX();
        this.dy -= other.getDY();
    }

    /**
     * X축 변위량
     *
     * @return X축 변위량
     */
    public int getDX() {
        return dx;
    }

    /**
     * Y축 변위량
     *
     * @return Y축 변위량
     */
    public int getDY() {
        return dy;
    }

    /**
     * X축 양의 방향을 기준으로한 벡터의 각도를 반환한다.
     *
     * @return 벡터 방향
     */
    public int getAngle() {
        // 벡터 방향(두축의 변위량으로 계산 가능. X축 변위량이 0일때 주의)
        if (dx == 0) {
            if (dy != 0) {
                return (dy > 0) ? 90 : -90;
            }
            return 0;
        }

        return (int) Math.atan((double) dy / dx);
    }

    /**
     * 벡터의 크기를 반환한다.
     *
     * @return 벡터 크기
     */
    public int getMagnitude() {
        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * 사용 목적 {@link World#setTranslate(int, int)}
     * @param dx 수정할 값
     */
    protected void setDX(int dx) {
        this.dx = dx;
    }

    /**
     * 사용 목적 {@link World#setTranslate(int, int)}
     * @param dy 수정할 값
     */
    protected void setDY(int dy) {
        this.dy = dy;
    }

    /**
     * X축 진행방향을 반대로 전환한다.
     */
    public void turnDX() {
        dx *= -1;
    }

    /**
     * Y축 진행방향을 반대로 전환한다.
     */
    public void turnDY() {
        dy *= -1;
    }

    @Override
    public String toString() {
        return String.format("Vector[dx = %d, dy = %d]", dx, dy);
    }
}
