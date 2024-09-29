package com.nhnacademy.game.util.paint;

import com.nhnacademy.game.util.shape.Shape;

import java.awt.Color;
import java.awt.Graphics;

public interface Paintable extends Shape {

    /**
     * 도형의 색상을 반환한다.
     * 
     * @return 도형의 색상
     */
    Color getColor();

    /**
     * 도형의 색상을 설정한다.
     * 
     * @param color 설정할 색상
     */
    void setColor(Color color);

    /**
     * 주어진 Graphics context 를 이용해 도형을 출력한다.
     *
     * @param g Graphics context
     */
    void paint(Graphics g);
}
