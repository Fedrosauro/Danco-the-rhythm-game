package gameplay;

public class Coordinate {

    private long start;
    private long finish; //useless for now

    public Coordinate(){
        start = 0;
        finish = 0;
    }

    public Coordinate(long start, long finish){
        this.start = start;
        this.finish = finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    } //for later

    public void setStart(long start) {
        this.start = start;
    }

    public long getFinish() {
        return finish;
    } //for later

    public long getStart() {
        return start;
    }

}
