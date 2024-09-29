package com.nhnacademy.game.util.shape;

import com.nhnacademy.game.util.shape.impl.ShapeImpl.ShapeType;
import com.nhnacademy.game.util.world.World;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.UUID;

public interface Shape {

    /**
     * 어떤 도형 타입인지 알려준다.
     *
     * @return 도형 타입
     */
    ShapeType getShapeType();

    /**
     * 객체의 고유한 식별자 값을 반환한다.
     *
     * @return 고유 식별자
     */
    UUID getId();

    /**
     * 객체의 이름을 반환한다.
     *
     * @return 이름
     */
    String getName();

    /**
     * 객체의 이름을 설정한다.
     *
     * @param name 이름
     */
    void setName(String name);

    /**
     * 도형이 차지하는 최소한의 영역을 반환한다.
     *
     * @return 도형의 최소 영역
     */
    Rectangle getBounds();

    /**
     * 도형의 중심 좌표값을 반환한다.
     *
     * @return 도형의 중심 좌표값
     */
    Point getLocation();

    /**
     * 도형의 중심 좌표값을 설정한다.
     *
     * @param location 도형의 중심 좌표값
     */
    void setLocation(Point location);

    /**
     * 도형의 중심 좌표값을 설정한다.
     *
     * @param x X축 좌표값
     * @param y Y축 좌표값
     */
    void setLocation(int x, int y);

    /**
     * 도형의 X축 중심 좌표값을 반환한다.
     *
     * @return 도형의 X축 중심 좌표값
     */
    int getCenterX();

    /**
     * 도형의 Y축 중심 좌표값을 반환한다.
     *
     * @return 도형의 Y축 중심 좌표값
     */
    int getCenterY();

    /**
     * 도형이 차지하는 영역 중에서 X축의 최소값을 반환한다.
     *
     * @return 영역 X축 최소값
     */
    int getMinX();

    /**
     * 도형이 차지하는 영역 중에서 X축의 최대값(MinX + Width)을 반환한다.
     *
     * @return 영역 X축 최대값
     */
    int getMaxX();

    /**
     * 도형이 차지하는 영역 중에서 Y축의 최소값을 반환한다.
     *
     * @return 영역 Y축 최소값
     */
    int getMinY();

    /**
     * 도형이 차지하는 영역 중에서 Y축의 최대값(MinY + Height)을 반환한다.
     *
     * @return 영역 Y축 최대값
     */
    int getMaxY();

    /**
     * 도형이 차지하는 최소한의 직사각형 형태의 영역 넓이를 반환한다.
     *
     * @return 영역 넓이
     */
    int getWidth();

    /**
     * 도형이 차지하는 최소한의 직사각형 형태의 영역 높이를 반환한다.
     *
     * @return 영역 높이
     */
    int getHeight();

    /**
     * 주어진 도형과 충돌되는지 확인한다.
     *
     * @param shape 대상 도형
     * @return 충돌 여부
     */
    boolean isCollision(Shape shape);

    /**
     * 주어진 도형과 겹치는 영역을 반환한다.
     *
     * @param shape 대상 도형
     * @return 중복 영역
     */
    Rectangle intersection(Shape shape);

    /**
     * 내가 소속된 World 정보를 설정합니다.<br>
     * 이후, World 객체를 통해 상호작용할 예정
     *
     * @param world 내가 소속된 World 정보
     */
    void setWorld(World world);

    /**
     * 객체에 대한 정보를 출력한다.
     *
     * @return 객체 정보
     */
    String toString();

    boolean equals(Object o);

    int hashCode();
}
