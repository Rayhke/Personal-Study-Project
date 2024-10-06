package com.nhnacademy.client.ui.listener;

import com.nhnacademy.client.ui.form.MessageClientForm;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

@Slf4j
public class SendButtonEventListener implements ActionListener {

    private final MessageClientForm messageClientForm;

    public SendButtonEventListener(MessageClientForm messageClientForm) {
        if (Objects.isNull(messageClientForm)) {
            throw new IllegalArgumentException("messageClientForm is Null!");
        }
        this.messageClientForm = messageClientForm;
    }

    // 현재 method 가, 클릭 이벤트가 발생했을 시, 동작하는 로직이다.
    // 그리고 메세지 보내기 버튼을 클릭했을 경우를 구현한거다.
    @Override
    public void actionPerformed(ActionEvent e) {
        log.debug("button actionCommand : {}", e.getActionCommand());
        /* messageClientForm.getSubject().sendMessage()를 이용해서 messageClientForm.getInputField().getText()를 보냅니다.
            - 즉, 메시지 전송 이벤트를 발생 시킴니다.
         */
        messageClientForm.getSubject().sendMessage(messageClientForm.getInputField().getText());

        // messageClientForm.getInputField()의 text 를 ""로 초기화 합니다.
        // 즉, 채팅 입력 창의 문자열 내용 초기화
        messageClientForm.getInputField().setText("");
    }
}
