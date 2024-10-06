package com.nhnacademy;

import com.nhnacademy.game.util.bound.Bounded;
import com.nhnacademy.game.util.bound.BoundedImpl;
import com.nhnacademy.game.util.config.DefaultWorld;
import com.nhnacademy.game.util.paint.impl.PaintableImpl;
import com.nhnacademy.game.util.shape.Shape;
import com.nhnacademy.game.util.shape.impl.ShapeImpl;
import com.nhnacademy.game.util.shape.impl.ShapeImpl.ShapeType;
import com.nhnacademy.game.util.world.MovableWorld;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private static final Shape[] shapeList = new Shape[] {
            new BoundedImpl()
                    .bounds(100, 100, 50, 50)
                    .dt(10)
                    .motion(7, 3)
                    .shapeType(ShapeType.BOX)
                    .color(Color.RED),
            new BoundedImpl()
                    .bounds(100, 300, 50, 50)
                    .dt(10)
                    .motion(5, 10)
                    .shapeType(ShapeType.BALL)
                    .color(Color.BLUE),
            new PaintableImpl()
                    .bounds(500, 500, 50, 200)
                    .color(Color.PINK)
                    .shapeType(ShapeType.BOX)
    };

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new Main();

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(DefaultWorld.DEFAULT_WIDTH, DefaultWorld.DEFAULT_HEIGHT);

        MovableWorld movableWorld = new MovableWorld();
        Thread thread = new Thread(movableWorld);

        frame.add(movableWorld);
        frame.setVisible(true);

        for (int n = 0; n < shapeList.length; n++) {
            movableWorld.add(shapeList[n]);
            shapeList[n].setWorld(movableWorld);
        }

        thread.start();
        Thread.sleep(500);

        while (true) {
            frame.repaint();
            Thread.sleep(1);
        }
    }
}
