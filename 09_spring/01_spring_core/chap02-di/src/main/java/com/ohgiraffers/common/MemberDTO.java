package com.ohgiraffers.common;

public class MemberDTO {

    private int sequence;
    private String name;
    //MemberDTO는 Account에 의존한다
    private Account personalAccount; //개인 계좌

    public MemberDTO() {
    }

    public MemberDTO(int sequence, String name, Account personalAccount) {
        this.sequence = sequence;
        this.name = name;
        this.personalAccount = personalAccount;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(Account personalAccount) {
        this.personalAccount = personalAccount;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "sequence=" + sequence +
                ", name='" + name + '\'' +
                ", personalAccount=" + personalAccount +
                '}';
    }
}
