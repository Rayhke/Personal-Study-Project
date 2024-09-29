package com.nhnacademy.game.util.move;

import com.nhnacademy.game.util.paint.Paintable;
import com.nhnacademy.game.util.vector.Motion;

public interface Movable extends Paintable, Runnable {

    /**
     * 움직임을 시작한다.
     */
    void start();

    /**
     * 움직임을 멈춘다.
     */
    void stop();

    /**
     * 도형의 변위량을 반환한다.
     * 
     * @return 변위량
     */
    Motion getMotion();

    /**
     * 도형의 변위량을 설정한다.
     * 
     * @param motion 변위량
     */
    void setMotion(Motion motion);

    /**
     * 도형의 변위량을 설정한다.
     * 
     * @param dx X축 변위량
     * @param dy Y축 변위량
     */
    void setMotion(int dx, int dy);

    /**
     * 도형의 X축 변위량을 설정한다.
     * 
     * @param dx X축 변위량
     */
    void setMotionToDX(int dx);

    /**
     * 도형의 Y축 변위량을 설정한다.
     * 
     * @param dy Y축 변위량
     */
    void setMotionToDY(int dy);

    /**
     * 도형을 설정된 변위량 만큼 이동 시킨다.
     */
    void move();

    /**
     * 도형을 지정된 좌표로 이동 시킨다.
     * 
     * @param x X축 위치
     * @param y Y축 위치
     */
    void moveTo(int x, int y);

    /**
     * 단위 시간을 반환한다.
     * 
     * @return 단위 시간
     */
    int getDT();

    /**
     * 단위 시간을 설정한다.
     * 
     * @param dt 단위 시간
     */
    void setDT(int dt);
}
