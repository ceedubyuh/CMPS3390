package cwomack.a10;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ListItem implements Serializable {
    private long ddtm;
    private String item;

    /**
     * Sets the inputted item
     * @param item user inputted list item
     */
    public ListItem(String item) {
        this.item = item;
        ddtm = System.nanoTime();

    }

    /**
     * Sets the item and linux datetime for each item
     * @param dttm linux date time
     * @param item user inputted item
     */
    public ListItem(long dttm, String item){
        this.ddtm = dttm;
        this.item = item;
    }

    /**
     * NonNull toString to return the name of each item
     * @return
     */
    @NonNull
    @Override
    public String toString(){
        return item;
    }

    /**
     * Getter for Linux Datetime
     * @return ddtm datetime
     */
    public long getDttm() {
        return ddtm;
    }

    /**
     * getter for inputted items
     * @return items
     */
    public String getItem() {
        return item;
    }
}
