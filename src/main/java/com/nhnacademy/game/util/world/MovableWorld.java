package com.nhnacademy.game.util.world;

import com.nhnacademy.game.util.move.Movable;
import com.nhnacademy.game.util.shape.Shape;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class MovableWorld extends World implements Runnable {

    private static final int DEFAULT_DT = 200;

    /**
     * 최대 이동 가능 횟수
     */
    private int maxMoveCount;

    /**
     * 이동 횟수
     */
    private int moveCount;

    /**
     * 이동간 시간적 간격으로 단위 시간
     */
    private int dt;

    private List<Thread> threadList;

    /**
     * Default Constructor<br>
     * 최대 이동 횟수를 임의로 설정하지 않으면, 무한으로 이동한다.
     */
    public MovableWorld() {
        this(0);
    }

    /**
     * Main Constructor<br>
     * 지정된 최대 이동 횟수 만큼, 도형들을 이동 시킬 수 있다.
     *
     * @param maxMoveCount 최대 이동 횟수
     * @throws IllegalArgumentException 최대 이동 가능 횟수가 올바르지 않을 경우
     */
    public MovableWorld(int maxMoveCount) {
        super();

        if (maxMoveCount < 0) {
            throw new IllegalArgumentException();
        }
        this.maxMoveCount = maxMoveCount;
        this.moveCount = 0;
        this.dt = DEFAULT_DT;
        this.threadList = new LinkedList<>();
    }

    // =================================================================================================================

    /**
     * 최대 이동 가능 횟수를 반환한다.
     *
     * @return 최대 이동 가능 횟수. 0은 무한 이동을 나타낸다.
     */
    public int getMaxMoveCount() {
        return maxMoveCount;
    }

    /**
     * @param maxMoveCount 최대 이동 횟수
     * @throws IllegalArgumentException 최대 이동 가능 횟수가 올바르지 않을 경우
     */
    public void setMaxMoveCount(int maxMoveCount) {
        if (maxMoveCount < 0) {
            throw new IllegalArgumentException();
        }
        this.maxMoveCount = maxMoveCount;
    }

    /**
     * 현재 이동 횟수를 반환한다.
     *
     * @return 현재 이동 횟수
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * 이동 횟수를 초기화한다.
     */
    public void reset() {
        this.moveCount = 0;
    }

    public void addMoveCount() {
        moveCount++;
    }

    /**
     * 단위 시간을 반환한다.
     *
     * @return 단위 시간
     */
    public int getDT() {
        return dt;
    }

    /**
     * 이동간 시간 간격인 단위 시간을 설정한다.
     *
     * @param dt 이동간 시간 간격
     * @throws IllegalArgumentException 주어진 시간 간격이 올바르지 않습니다. (dt < 0)
     */
    public void setDT(int dt) {
        if (dt < 0) {
            throw new IllegalArgumentException();
        }
        this.dt = dt;
    }

    /**
     * 이동 횟수가 최대 제한 횟수를 넘지 않을 경우, 이동 가능한 볼을 1회 이동시킨다.
     *
     * @deprecated 도형 객체들이 Thread 구조로 되면서 스스로 move 를 반복하게 됬기 때문에 이후, 로직을 수정해야함
     */
    public void move() {
        if (moveWhetherCheck()) {
            for (int n = 0; n < super.getShapeCount(); n++) {
                Shape shape = super.getShape(n);

                if (shape instanceof Movable) {   // Movable.interface 의 구현체인 객체인가? 아닌가?
                    log.info("Movable : {}", shape);
                    ((Movable) shape).move();
                }
            }
        }
        moveCount++;
        repaint();
    }

    public synchronized void start() {
        for (Shape shape : shapeList) {
            if (shape instanceof Movable) {
                threadList.add(new Thread((Movable) shape));
            }
        }
    }

    public synchronized void stop() {
        for (Thread thread : threadList) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
    }

    /**
     * 이동 횟수가 최대 제한 횟수를 넘지 않을때까지 이동 시킨다.
     *
     * @throws InterruptedException 스레드와 관련된 문제가 발생할 경우
     */
    @Override
    public void run() {
        this.start();

        for (Thread thread : threadList) {
            thread.start();
        }
    }

    // ================================================================================

    /**
     * ball 을 이동 가능한 지, 여부를 체크
     *
     * @return 최대 제한 횟수가 무한이거나, <br>또는 이동 횟수가 최대 제한 횟수를 넘지 않을 경우 true
     */
    protected boolean moveWhetherCheck() {
        return getMaxMoveCount() == 0
                || getMoveCount() < getMaxMoveCount();
    }
}
