package com.nhnacademy.client.ui.listener;

import com.nhnacademy.client.ui.form.MessageClientForm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MessageAreaChangeListener implements DocumentListener {

    private final MessageClientForm messageClientForm;

    public MessageAreaChangeListener(MessageClientForm messageClientForm) {
        this.messageClientForm = messageClientForm;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        handleTextChange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handleTextChange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        handleTextChange();
    }

    private void handleTextChange() {
        // scroll 하단으로 이동
        JScrollBar verticalScrollBar = messageClientForm.getScrollPane().getVerticalScrollBar();
        SwingUtilities.invokeLater(() -> verticalScrollBar.setValue(verticalScrollBar.getMaximum()));
    }
}
