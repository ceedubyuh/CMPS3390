package cwomack.a3;
/**
 * Shape class for A3
 * @author Carter Womack
 * @version 1.0
 */
public class Shape{
    private Type type;

    public Shape() {
        this.type = Type.SHAPE;
    }
    public Type getType() {
        return this.type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return String.format("Type: %-12s | ", this.type);
    }
}
