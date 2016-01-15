package aganaktish;

import java.io.*;

class Pair implements Serializable {

    private String id;
    private int counter;

    protected Pair(String id, int counter) {
        this.id = id;
        this.counter = counter;
    }

    protected int getCounter() {
        return counter;
    }

    protected void setCounter(int counter) {
        this.counter = counter;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected String getId() {
        return id;
    }
}