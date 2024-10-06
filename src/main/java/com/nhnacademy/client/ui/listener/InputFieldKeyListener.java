package com.nhnacademy.client.ui.listener;

import com.nhnacademy.client.ui.form.MessageClientForm;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class InputFieldKeyListener implements KeyListener {

    private final MessageClientForm messageClientForm;

    public InputFieldKeyListener(MessageClientForm messageClientForm) {
        if (Objects.isNull(messageClientForm)) {
            throw new IllegalArgumentException("messageClientForm is Null!");
        }
        this.messageClientForm = messageClientForm;
    }

    // TODO : 추후 구현해야 하는 지 확인
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 사실상 KeyListener 를 한 이유가 메세지 보내기 버튼을
    // KeyBoard 의 Enter 입력을 감지하려는 목적
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        /* enterkey를 누를 때 발생하는 이벤트를 처리 합니다.
            - inputBox 에서 enter 를 입력하면 SendButtonEventListener에 구현 했던것 처럼 messageClientForm.getSubject().sendMessage()를 이용해서 메시지를 전송 합니다.
            - 즉 전송 이벤트를 발생 시킴니다.
        */
            messageClientForm.getSubject().sendMessage(messageClientForm.getInputField().getText());
            messageClientForm.getInputField().setText("");
        }
    }

    // TODO : 추후 구현해야 하는 지 확인
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
