package com.ohgiraffers.section00.plainjava;

import com.ohgiraffers.common.Account;
import com.ohgiraffers.common.MemberDTO;
import com.ohgiraffers.common.PersonalAccount;

import java.lang.reflect.Member;

public class Application {
    public static void main(String[] args) {

        /*Spring 없이 직접 객체 생성하고 연결*/
        Account account = new PersonalAccount(20, "110-234-567890");

        MemberDTO member = new MemberDTO();
        member.setSequence(1);
        member.setName("홍길동");
        member.setPersonalAccount(account);

        System.out.println(member.getPersonalAccount());
        System.out.println(member.getPersonalAccount().deposit(10000));
        System.out.println(member.getPersonalAccount().getBalance());
        System.out.println(member.getPersonalAccount().withDraw(5000));
        System.out.println(member.getPersonalAccount().getBalance());






    }



}
