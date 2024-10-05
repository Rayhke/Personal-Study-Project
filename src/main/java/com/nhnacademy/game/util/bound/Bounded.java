package com.nhnacademy.game.util.bound;

import com.nhnacademy.game.util.move.Movable;

import java.awt.*;

public interface Bounded extends Movable {

    /**
     * 도형이 움직일 수 있는 경계 영역을 반환한다.
     *
     * @return 경계 영역
     */
    Rectangle getBoundedArea();

    /**
     * 도형이 움직일 수 있는 경계 영역을 설정한다.
     *
     * @param boundedArea 경계 영역
     */
    void setBoundedArea(Rectangle boundedArea);

    /**
     * 도형이 움직일 수 있는 경계 영역의 중심 좌표값을 반환한다.
     *
     * @return 경계 영역의 중심 좌표값
     */
    Point getAreaLocation();

    /**
     * 도형의 주변 경계 영역 내에서 X축 최소값을 반환한다.
     *
     * @return 경계 영역의 X축 최소값
     */
    int getAreaMinX();

    /**
     * 도형의 주변 경계 영역 내에서 X축 최대값(MinX + Width)을 반환한다.
     *
     * @return 경계 영역의 X축 최대값
     */
    int getAreaMaxX();

    /**
     * 도형의 주변 경계 영역 내에서 Y축 최소값을 반환한다.
     *
     * @return 경계 영역의 Y축 최소값
     */
    int getAreaMinY();

    /**
     * 도형의 주변 경계 영역 내에서 Y축 최대값(MinY + Height)을 반환한다.
     *
     * @return 경계 영역의 Y축 최대값
     */
    int getAreaMaxY();

    /**
     * 도형의 주변 경계 영역의 너비(폭)를 반환한다.
     *
     * @return 경계 영역의 너비
     */
    int getAreaWidth();

    /**
     * 도형의 주변 경계 영역의 높이를 반환한다.
     *
     * @return 경계 영역의 높이
     */
    int getAreaHeight();

    /**
     * 도형이 이동하려는 위치가 경계 영역을 벗어 나는지 확인한다.
     *
     * @param otherBounds 이동하려는 영역
     * @return 이동하려는 곳이 경계 영역을 벗어날 경우 ? true : false
     */
    boolean isOutOfBounds(Rectangle otherBounds);

    /**
     * 도형이 이동하려는 위치가 지정된 경계 영역을 벗어날 경우, 튕겨낸다.
     *
     * @param otherBounds 이동하려는 영역
     */
    void bounce(Rectangle otherBounds);
}
