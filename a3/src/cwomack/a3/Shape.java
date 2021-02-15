package cwomack.a3;
/**
 * Shape class for A3
 * @author Carter Womack
 * @version 1.0
 */
public class Shape{
    private Type type;

    /**
     * Default constructor to set the type to shape
     */
    public Shape() {
        this.type = Type.SHAPE;
    }

    /**
     * Gets the type of shape
     * @return type enum representing the type of shape given
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Sets the type of shape
     * @param type enum that represents the type of the shape
     */
    public void setType(Type type) {
        this.type = type;
    }

    public double getArea() {
        return 0;
    }

    /**
     * OVerride of the toString function to print shapes nicely
     * @return string that represents all properties of a shape
     */
    @Override
    public String toString(){
        return String.format("Type: %-12s  | ", this.type);
    }
}
