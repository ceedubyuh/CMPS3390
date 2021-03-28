package cwomack.a10;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ListItem implements Serializable {
    private long ddtm;
    private String item;

    public ListItem(String item) {
        this.item = item;
        ddtm = System.nanoTime();

    }

    public ListItem(long dttm, String item){
        this.ddtm = dttm;
        this.item = item;
    }

    @NonNull
    @Override
    public String toString(){
        return item;
    }

    public long getDttm() {
        return ddtm;
    }

    public String getItem() {
        return item;
    }
}
