package com.myshop.order.command.domain;

public class Canceller {
    private String memberId;

    public Canceller(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}
