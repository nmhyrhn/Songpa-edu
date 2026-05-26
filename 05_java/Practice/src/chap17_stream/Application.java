package chap17_stream;

import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        TicketService service = new TicketService();

        System.out.println("[전체 영화 목록]");
        service.findAll();

        System.out.println();
        System.out.println("[130분 이상 Action 영화 제목]");
        List<String> longActionTitles = service.findLongActionTitles();
        longActionTitles.forEach(title -> System.out.println(title));

        System.out.println();
        System.out.println("[예매 완료 티켓 가격 낮은 순]");
        List<MovieTicket> reservedTickets = service.findReservedTicketSorted();
        reservedTickets.forEach(ticket ->
                System.out.println(ticket.getTitle() + " / " + ticket.getPrice() + "원")
        );

        System.out.println();
        System.out.println("[장르 목록]");
        List<String> genres = service.findGenres();
        System.out.println(genres);

        System.out.println();
        System.out.println("청소년 관람불가 영화 수: " + service.countAdultMovies());

        System.out.println();
        System.out.println("Top Gun: Maverick 존재 여부: " + service.hasMovie("Top Gun: Maverick"));

        System.out.println();
        System.out.println("예매 완료 총 결제 금액: " + service.getTotalReservedPrice() + "원");

        System.out.println();
        System.out.println("[장르별 영화]");
        Map<String, List<MovieTicket>> groupMap = service.groupByGenre();

        groupMap.forEach((genre, movieList) -> {
            System.out.print(genre + ": ");
            movieList.forEach(ticket -> System.out.print(ticket.getTitle() + ", "));
            System.out.println();
        });
    }
}