package chap17_stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketService {

    private ArrayList<MovieTicket> tickets = new ArrayList<>();

    public TicketService() {
        tickets = new ArrayList<>();
        tickets.add(new MovieTicket("Avatar", "SF", 162, 15000, 12, true));
        tickets.add(new MovieTicket("Avatar: The Way of Water", "SF", 192, 16000, 12, false));
        tickets.add(new MovieTicket("Avengers: Endgame", "Action", 181, 16000, 12, true));
        tickets.add(new MovieTicket("Avengers: Infinity War", "Action", 149, 15000, 12, false));
        tickets.add(new MovieTicket("Top Gun: Maverick", "Action", 130, 14000, 12, true));
        tickets.add(new MovieTicket("Deadpool & Wolverine", "Action", 128, 17000, 19, true));
        tickets.add(new MovieTicket("Inside Out 2", "Animation", 96, 12000, 0, true));
        tickets.add(new MovieTicket("Frozen II", "Animation", 103, 11000, 0, false));
    };

    public void findAll() {
        tickets.stream().forEach(ticket -> System.out.println(ticket));
    }

    public List<String> findLongActionTitles() {
        return tickets.stream()
                .filter(ticket -> ticket.getGenre().equals("Action"))
                .filter(ticket -> ticket.getRunningTime() >- 130)
                .map(ticket -> ticket.getTitle())
                .collect(Collectors.toList());
    }

    public List<MovieTicket> findReservedTicketSorted() {
        return tickets.stream()
                .filter(ticket -> ticket.isReserved())
                .sorted((t1, t2) -> Integer.compare(t1.getPrice(), t2.getPrice()))
                .collect(Collectors.toList());
    }

    public List<String> findGenres() {
        return tickets.stream()
                .map(ticket -> ticket.getGenre())
                .distinct()
                .collect(Collectors.toList());
    }

    public long countAdultMovies() {
        return tickets.stream()
                .filter(ticket -> ticket.getAgeLimit() == 19)
                .count();
    }

    public boolean hasMovie(String title) {
        return tickets.stream()
                .anyMatch(ticket -> ticket.getTitle().equals(title));
    }

    public int getTotalReservedPrice() {
        return tickets.stream()
                .filter(ticket -> ticket.isReserved())
                .mapToInt(ticket -> ticket.getPrice())
                .sum();
    }

    public Map<String, List<MovieTicket>> groupByGenre() {
        return tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getGenre()));
    }
}