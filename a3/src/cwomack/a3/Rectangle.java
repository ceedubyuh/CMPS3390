package cwomack.a3;
/**
 * Rectangle class for A3
 * @author Carter Womack
 * @version 1.0
 */
public class Rectangle extends Square{
    private float height;

    /**
     * Constructor for rectangle class
     */
    public Rectangle(){
        super();
        this.setType(Type.RECTANGLE);
        this.setWidth(0);
    }

    /**
     * Sets the width and height of the rectangle
     * @param width
     * @param height
     */
    public Rectangle(float width, float height){
        super();
        this.setWidth(width);
        this.height = height;
    }
    public float getHeight(){
        return this.height;
    }
    public void setHeight(float height){
        this.height = height >= 0 ? height : 0;
    }

    /**
     * Override getArea from parent
     * @return equation to find area of a rectangle
     */
    @Override
    public double getArea(){
        return this.getHeight() * getWidth();
    }

    /**
     * Override toString from parent to add Height
     * @return height as a string
     */
    @Override
    public String toString(){
        return String.format("%s Height: %-10.2f", super.toString(), this.getHeight());
    }
}
