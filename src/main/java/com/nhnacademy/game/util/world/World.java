package com.nhnacademy.game.util.world;

import com.nhnacademy.game.util.config.DefaultMotion;
import com.nhnacademy.game.util.config.DefaultShape;
import com.nhnacademy.game.util.vector.Motion;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class World extends JPanel {

    public enum Axis {
        AXIS_X, AXIS_Y
    }

    private final Rectangle DEFAULT_BOUNDED_AREA;

    protected Motion COMMON_FIELD_MOTION = null;

    protected List<Shape> shapeList = null;

    public World() {
        super();
        this.DEFAULT_BOUNDED_AREA = new Rectangle(DefaultShape.DEFAULT_MIN_X,
                                                DefaultShape.DEFAULT_MIN_Y,
                                                DefaultShape.DEFAULT_WIDTH,
                                                DefaultShape.DEFAULT_HEIGHT);
        this.COMMON_FIELD_MOTION = new Motion(DefaultMotion.DEFAULT_DX, DefaultMotion.DEFAULT_DY);
        this.shapeList = new LinkedList<>();
    }



}
