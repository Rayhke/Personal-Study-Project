package com.nhnacademy.client.ui.form;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.action.impl.RecvMessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.observer.impl.MessageRecvObserver;
import com.nhnacademy.client.event.subject.EventType;
import com.nhnacademy.client.event.subject.Subject;
import com.nhnacademy.client.ui.listener.InputFieldKeyListener;
import com.nhnacademy.client.ui.listener.MessageAreaChangeListener;
import com.nhnacademy.client.ui.listener.SendButtonEventListener;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@Slf4j
public class MessageClientForm {

    private JFrame frame;

    private JPanel panel;

    private JTextArea messageArea;

    private JTextField inputField;

    private JButton sendButton;

    private JScrollPane scrollPane;

    private final Subject subject;

    public MessageClientForm(Subject subject) {
        if (Objects.isNull(subject)) {
            throw new IllegalArgumentException("subject is Null!");
        }

        this.subject = subject;
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.messageArea = new JTextArea();
        this.inputField = new JTextField();
        this.sendButton = new JButton();
        this.scrollPane = new JScrollPane(messageArea);

        // ui 를 초기화 합니다.
        initializeUi();

        // event 를 설정 합니다.
        configureEvent();

        // 수신 event 를 설정 합니다.
        configureRecvObserver();
    }

    // =================================================================================================================

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public Subject getSubject() {
        return subject;
    }

    // =================================================================================================================

    private void initializeUi() {

        messageArea.setEditable(false);
        // 자동 줄바꿈, 가로 스크롤 생김 방지
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setRows(50);
        messageArea.setColumns(100);

        inputField.setColumns(100);

        sendButton.setText("Send");

        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.setTitle("Message Client");
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 화면 크기 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // 프레임 크기 가져오기
        Dimension frameSize = frame.getSize();

        // 프레임을 화면 가운데로 이동
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    private void configureEvent() {

        /* sendButton : event, send button을 클릭시 발생하는 이벤트 핸들러를 등록 합니다.
            - event listener : SendButtonEventListener
         */
        sendButton.addActionListener(new SendButtonEventListener(this));

        /* inputField : key event, inputbox에 message를 입력 후 enter key를 누르면 메시지가 전송 됩니다.
            -  event listener : InputFieldKeyListener
         */
        inputField.addKeyListener(new InputFieldKeyListener(this));

        /* messageArea : text change event, 서버로부터 message를 수신하면 UI에 message가 append 됩니다. 이 때 scroll을 맨 하단으로 이동시켜 UI접근성을 향상시키기 위해서 해당 이벤트를 등록 합니다.
            - event listener : MessageAreaChangeListener
         */
        messageArea.getDocument().addDocumentListener(new MessageAreaChangeListener(this));
    }

    private void configureRecvObserver() {
        /* server로 부터 message를 수신하면 MessageRecvObserver에 의해서 messageAction를 호출하여 UI(MessageClientForm)에 반영 합니다.
            - message를 수신한 Observer를 subject에 등록 하세요
            - EventType.RECV 입니다.
         */
        MessageAction recvMessageAction = new RecvMessageAction(this);
        Observer observer = new MessageRecvObserver(recvMessageAction);
        subject.register(EventType.RECV, observer);
    }

    public static void showUI(Subject subject) {
        SwingUtilities.invokeLater(() -> {
            // 이렇게 되는 이유는 SwingUtilities.invokeLater(Runnable runnable) 형식이기 때문
            // 즉, 아래의 class 를 Runnable 타입을 상속한 것 처럼 만들어버림.
            new MessageClientForm(subject);
        });
    }
}
