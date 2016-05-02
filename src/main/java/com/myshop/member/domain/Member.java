package com.myshop.member.domain;

import com.myshop.common.event.Events;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Random;

@Entity
public class Member {
    @EmbeddedId
    private MemberId id;

    private String name;
    @Embedded
    private Password password;

    private boolean blocked;

    protected Member() {
    }

    public Member(MemberId id, String name) {
        this.id = id;
        this.name = name;
    }

    public MemberId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void initializePassword() {
        String newPassword = generateRandomPassword();
        this.password = new Password(newPassword);
        Events.raise(new PasswordChangedEvent(id.getId(), newPassword));
    }

    private String generateRandomPassword() {
        Random random = new Random();
        int number = random.nextInt();
        return Integer.toHexString(number);
    }

    public void block() {
        this.blocked = true;
        Events.raise(new MemberBlockedEvent(id.getId()));
    }

    public void unblock() {
        this.blocked = false;
        Events.raise(new MemberUnblockedEvent(id.getId()));
    }

    public void changePassword(String oldPw, String newPw) {
        if (!password.match(oldPw)) {
            throw new IdPasswordNotMatchingException();
        }
        this.password = new Password(newPw);
        Events.raise(new PasswordChangedEvent(id.getId(), newPw));
    }
}
