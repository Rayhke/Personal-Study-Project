package com.nhnacademy.game.util.vector;

import com.nhnacademy.game.util.config.DefaultMotion;

import java.util.Objects;

public class Motion extends Vector {

    /**
     * Default
     */
    public Motion() {
        super(DefaultMotion.DEFAULT_DX, DefaultMotion.DEFAULT_DY);
    }

    public Motion(int dx, int dy) {
        super(dx, dy);
    }

    public Motion(Vector vector) {
        this(vector.getDX(), vector.getDY());
    }

    public Motion(Motion motion) {
        this(motion.getDX(), motion.getDY());
    }

    public void setMotion(Motion motion) {
        setMotion(motion.getDX(), motion.getDY());
    }

    public void setMotion(int dx, int dy) {
        super.dx = dx;
        super.dy = dy;
    }

    public String toString() {
        return String.format("motion(dx=%d, dy=%d)", dx, dy);
    }
}
