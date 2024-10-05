package com.nhnacademy.game.util.world;

import com.nhnacademy.game.error.AlreadyExistException;
import com.nhnacademy.game.error.DuplicatedBoundsException;
import com.nhnacademy.game.error.OutOfBoundsException;
import com.nhnacademy.game.util.config.DefaultMotion;
import com.nhnacademy.game.util.config.DefaultShape;
import com.nhnacademy.game.util.shape.Shape;
import com.nhnacademy.game.util.vector.Motion;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class World extends JPanel {

    public enum Axis {
        AXIS_X, AXIS_Y
    }

    private final Rectangle DEFAULT_BOUNDED_AREA;

    protected Motion COMMON_FIELD_MOTION = null;

    protected List<Shape> shapeList = null;

    public World() {
        super();
        this.shapeList = new LinkedList<>();
        this.COMMON_FIELD_MOTION = new Motion(DefaultMotion.DEFAULT_DX,
                                                DefaultMotion.DEFAULT_DY);
        this.DEFAULT_BOUNDED_AREA = new Rectangle(DefaultShape.DEFAULT_MIN_X,
                                                    DefaultShape.DEFAULT_MIN_Y,
                                                    DefaultShape.DEFAULT_WIDTH,
                                                    DefaultShape.DEFAULT_HEIGHT);
    }

    public synchronized void add(Shape shape) {
        if (Objects.isNull(shape)) {
            throw new NullPointerException();
        }

        if (shapeList.contains(shape)) {
            throw new AlreadyExistException(shape.getId());
        }

        if (shape.getMinX() < super.getBounds().getMinX()
            || super.getBounds().getMaxX() < shape.getMaxX()
            || shape.getMinY() < super.getBounds().getMinY()
            || super.getBounds().getMaxY() < shape.getMaxY()) {
            log.debug("==============================");
            log.error("[World.class] : 충돌 발생");
            log.error("World Area : {}", super.getBounds());
            log.error("Shapes Area : {}", shape.getBounds());
            log.debug("==============================");
            throw new OutOfBoundsException();
        }

        for (Shape other : shapeList) {
            if (other.getBounds().intersects(shape.getBounds())) {
                throw new DuplicatedBoundsException();
            }
        }

    }
}
