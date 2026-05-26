package chap17_stream;

public class MovieTicket {

    private String title;
    private String genre;
    private int runningTime;
    private int price;
    private int ageLimit;
    private boolean reserved;

    public MovieTicket(String title, String genre, int runningTime, int price, int ageLimit, boolean reserved) {
        this.title = title;
        this.genre = genre;
        this.runningTime = runningTime;
        this.price = price;
        this.ageLimit = ageLimit;
        this.reserved = reserved;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public int getPrice() {
        return price;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public boolean isReserved() {
        return reserved;
    }

    @Override
    public String toString() {
        return "MovieTicket{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", runningTime=" + runningTime +
                ", price=" + price +
                ", ageLimit=" + ageLimit +
                ", reserved=" + reserved +
                '}';
    }
}
