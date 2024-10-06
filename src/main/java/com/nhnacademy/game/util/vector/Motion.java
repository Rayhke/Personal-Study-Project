package com.nhnacademy.game.util.vector;

import com.nhnacademy.game.util.config.DefaultMotion;

/**
 * 도형의 변위량
 */
public class Motion extends Vector {

    /**
     * Default Constructor
     */
    public Motion() {
        this(DefaultMotion.DEFAULT_DX, DefaultMotion.DEFAULT_DY);
    }

    /**
     * Copy Constructor
     *
     * @param motion 객체
     */
    public Motion(Motion motion) {
        this(motion.getDX(), motion.getDY());
    }

    /**
     * Super Constructor
     *
     * @param vector 부모 객체
     */
    public Motion(Vector vector) {
        this(vector.getDX(), vector.getDY());
    }

    /**
     * Main Constructor
     *
     * @param dx X축 변위량
     * @param dy Y축 변위량
     */
    public Motion(int dx, int dy) {
        super(dx, dy);
    }

    public void setMotion(int dx, int dy) {
        super.dx = dx;
        super.dy = dy;
    }

    public String toString() {
        return String.format("Motion[dx = %d, dy = %d]", dx, dy);
    }
}
