package com.nhnacademy.game.util.paint.impl;

import com.nhnacademy.game.error.ShapeNotFoundException;
import com.nhnacademy.game.util.config.DefaultPaintable;
import com.nhnacademy.game.util.paint.Paintable;
import com.nhnacademy.game.util.shape.impl.ShapeImpl;
import com.nhnacademy.game.util.world.World;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Objects;
import java.util.UUID;

public class PaintableImpl extends ShapeImpl implements Paintable {

    private Color color;

    public PaintableImpl() {
        super();
        this.color = DefaultPaintable.DEFAULT_COLOR;
    }

    // =================================================================================================================

    @Override
    public PaintableImpl shapeType(ShapeType shapeType) {
        super.shapeType(shapeType);
        return this;
    }

    @Override
    public PaintableImpl id(UUID id) {
        super.id(id);
        return this;
    }

    @Override
    public PaintableImpl name(String name) {
        super.name(name);
        return this;
    }

    @Override
    public PaintableImpl bounds(Rectangle bounds) {
        return bounds((int) bounds.getMinX(),
                        (int) bounds.getMinY(),
                        (int) bounds.getWidth(),
                        (int) bounds.getHeight());
    }

    @Override
    public PaintableImpl bounds(int minX, int minY, int width, int height) {
        super.bounds(minX, minY, width, height);
        return this;
    }

    @Override
    public PaintableImpl world(World world) {
        super.world(world);
        return this;
    }

    public PaintableImpl color(Color color) {
        if (Objects.isNull(color)) {
            throw new IllegalArgumentException("color is Null!");
        }
        this.color = color;
        return this;
    }

    // =================================================================================================================

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        if (Objects.isNull(color)) {
            throw new IllegalArgumentException("color is Null!");
        }
        this.color = color;
    }

    @Override
    public void paint(Graphics g) {
        if (Objects.isNull(g)) {
            throw new NullPointerException();
        }

        Color backupColor = g.getColor();
        g.setColor(getColor());

        ShapeType shapeType = super.getShapeType();
        if (shapeType.equals(ShapeType.BALL)) {
            g.fillOval(getMinX(), getMinY(), getWidth(), getHeight());
        } else if (shapeType.equals(ShapeType.BOX)) {
            g.fillRect(getMinX(), getMinY(), getWidth(), getHeight());
        } else {
            // 추후 새로운 도형 타입이 추가되면, 내용 추가
            throw new ShapeNotFoundException();
        }
        g.setColor(backupColor);
    }
}
