package cwomack.a3;
/**
 * Square class for A3
 * @author Carter Womack
 * @version 1.0
 */
public class Square extends Shape{
    private float width;

    /**
     * Default constructor that sets the type and width
     */
    public Square() {
        super();
        this.setType(Type.SQUARE);
        this.setWidth(0);
    }

    /**
     * Override constructor that sets the type and width
     * @param width float that represents the width of a square
     */
    public Square(float width){
        super();
        this.setType(Type.SQUARE);
        this.width = width;
    }

    /**
     * Gets the width of a square
     * @return width as a float
     */
    public float getWidth(){
        return this.width;
    }

    /**
     * Sets the width of a square
     * @param width float that represents the width of a square
     */
    public void setWidth(float width){
        this.width = width >= 0 ? width : 0;
    }

    /**
     * Gets the area of a square
     * @return area as a float
     */
    public double getArea() {
        return this.width * this.width;
    }

    /**
     * Override parent toString to add Width
     * @return Width as a string
     */
    @Override
    public String toString(){
        return String.format("%s Width: %-10.2f", super.toString(), this.getWidth());
    }
}
