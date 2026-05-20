package com.ohgiraffers.servletlifecycle;


/*
* Servlet
* - Serve + Applet (작은 Application)
* - HTTP 요청을 처리할 수 있는 자바 클래
*
* - HttpServlet (웹 요청을 처리하는데 필요한 설정을 가진 클래스) 상속해서 커스텀 서블릿을 생성할 수 있다.
* - HttpServlet 상속구조
*   - Servlet interface
*       - GenericServlet class
*           - HttpServlet class ( 웹 Http 처리용 클래스)
*               - CustomServlet
* - Tomcat은 요청별로 Servlet 객체로 관리한다 (Servlet/JSP Container)
* - Tomcat은 Servlet 객체를 Singleton 패턴으로 관리한다.
* - url별 첫 번째 요청에 tomcat은 Servlet 객체를 생성하고, init(ServletConfig) 메소드를 실행
* - 실제 요청을 처리하는 것은 service 메소드 들이다.
*   1. 요청이 오면, service(ServletRequest, ServletResponse)가 호출된다.
*   2. 전송방식별로 그에 상응하는 메소드 호출 doGet, doPost 등이 있다.
* - Tomcat 종료시에 모든 Servlet 객체를 destroy 메소드 호출 후 반환한다.
* */

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(value = "/lifecycle", loadOnStartup = 1)
public class LifeCycleTestServlet extends HttpServlet {

    private int initCount = 1;
    private int serviceCount = 1;
    private int destroyCount = 1;

    public LifeCycleTestServlet() {
        System.out.println("constructor 생성자!");
    }

    //서블릿이 생성될 때 최초 단 한번 호출되어 초기화 작업을 담당
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init(): " + initCount++);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service(): " + initCount++);
    }

    @Override
    public void destroy() {
        System.out.println("destroy(): " + destroyCount++);
    }

}

/*
* 스레드(Thread)
* 프로그램:깔려있는 프로그램
* 프로세스:실행중인 프로그램
* 프로세스를 실행해 처리하는게 스레드
*
*
* */