package com.nhnacademy.game.util.world;

import com.nhnacademy.game.error.AlreadyExistException;
import com.nhnacademy.game.error.DuplicatedBoundsException;
import com.nhnacademy.game.error.OutOfBoundsException;
import com.nhnacademy.game.util.bound.Bounded;
import com.nhnacademy.game.util.config.DefaultBounded;
import com.nhnacademy.game.util.config.DefaultMotion;
import com.nhnacademy.game.util.config.DefaultShape;
import com.nhnacademy.game.util.move.Movable;
import com.nhnacademy.game.util.paint.Paintable;
import com.nhnacademy.game.util.shape.Shape;
import com.nhnacademy.game.util.vector.Motion;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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
        this.DEFAULT_BOUNDED_AREA = new Rectangle(DefaultBounded.DEFAULT_AREA_MIN_X,
                                                    DefaultBounded.DEFAULT_AREA_MIN_Y,
                                                    DefaultBounded.DEFAULT_AREA_WIDTH,
                                                    DefaultBounded.DEFAULT_AREA_HEIGHT);
    }

    /**
     * World 영역에 도형을 추가한다.
     *
     * @param shape 등록할 도형
     * @throws NullPointerException      추가할 도형이 null 로 주어질 경우
     * @throws AlreadyExistException     추가할 도형이 이미 등록되어 있는 경우
     * @throws OutOfBoundsException      추가할 도형이 World 영역을 벗어나 등록할 수 없을 경우
     * @throws DuplicatedBoundsException 추가할 도형의 영역이 등록되어 있는 도형의 영역과 충돌할 경우
     */
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

        /*
         * 만약, shape 에 사용자가 임의로 경계 영역을 설정하지 않았을 경우
         * World 의 경계 영역으로 사용한다.
         */
        if (shape instanceof Bounded
                && ((Bounded) shape).getBoundedArea().equals(DEFAULT_BOUNDED_AREA)) {
            ((Bounded) shape).setBoundedArea(super.getBounds());
        }

        shape.setWorld(this);
        shapeList.add(shape);
        log.info("도형이 추가 되었습니다 : {}", shape);
    }

    /**
     * World 에서 존재하는 특정 도형을 찾아서 제거한다.
     *
     * @param shape 제거할 도형 (관리 목록에서 삭제)
     * @throws NullPointerException   제거할 도형이 null 로 주어질 경우
     * @throws NoSuchElementException 제거할 도형이 없는 경우
     */
    public synchronized void remove(Shape shape) {
        if (Objects.isNull(shape)) {
            throw new NullPointerException();
        }
        if (!shapeList.remove(shape)) {
            throw new NoSuchElementException();
        }
        log.info("도형이 제거 되었습니다 : {}", shape);
    }

    /**
     * 도형 목록에서 지정된 순서의 도형을 제거한다.
     *
     * @param index 제거한 도형의 순번
     * @throws IndexOutOfBoundsException 주어진 순번의 도형이 존재하지 않을 경우
     * @throws IndexOutOfBoundsException List 내부에 index 범위를 벗어나는 경우
     */
    @Override
    public synchronized void remove(int index) {
        shapeList.remove(index);
        log.info("[{}번째] 도형이 제거 되었습니다", index);
    }

    /**
     * World 에 등록된 도형의 갯수를 반환한다.
     *
     * @return 등록된 도형 갯수
     */
    public int getShapeCount() {
        return shapeList.size();
    }

    /**
     * World 에 등록된 도형 중에 주어진 순번의 도형을 반환한다.
     *
     * @param index 도형 순번
     * @return      도형
     * @throws IndexOutOfBoundsException 주어진 순번의 도형이 존재하지 않을 경우
     */
    public Shape getShape(int index) {
        return shapeList.get(index);
    }

    /**
     * Graphics context 를 이용해 등록된 도형을 출력한다.
     *
     * @param g Graphics context
     * @throws NullPointerException 입력받은 Graphics 값이 Null 일 경우
     */
    @Override
    public synchronized void paint(Graphics g) {
        if (Objects.isNull(g)) {
            throw new NullPointerException();
        }
        for (Shape shape : shapeList) {
            if (shape instanceof Paintable) {
                log.info("Paint : {}", shape);
                ((Paintable) shape).paint(g);
            }
        }
    }
}
