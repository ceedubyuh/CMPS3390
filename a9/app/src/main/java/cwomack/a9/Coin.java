package cwomack.a9;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.text.NumberFormat;
import java.util.Locale;

public class Coin extends BaseObservable{
    private String name;
    private double currentValue;

    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

    public Coin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Bindable
    public String getCurrentValue() {
        return numberFormat.format(currentValue);
    }
    @Bindable
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
        notifyPropertyChanged(BR.currentValue);
    }
}
