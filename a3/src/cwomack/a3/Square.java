package cwomack.a3;
/**
 * Square class for A3
 * @author Carter Womack
 * @version 1.0
 */
public class Square extends Shape{
    private float width;

    public Square() {
        super();
        this.setType(Type.SQUARE);
    }

    /**
     * Sets the squares width to given number
     * @param width
     */
    public Square(float width){
        super();
        this.width = width;
    }
    public float getWidth(){
        return this.width;
    }
    public void setWidth(float width){
        this.width = width >= 0 ? width : 0;
    }
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
