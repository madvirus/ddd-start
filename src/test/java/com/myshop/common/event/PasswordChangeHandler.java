package com.myshop.common.event;

import com.myshop.member.domain.PasswordChangedEvent;

public class PasswordChangeHandler implements EventHandler<PasswordChangedEvent> {
    @Override
    public void handle(PasswordChangedEvent event) {
    }
}
