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
     * Sets shape type to rectangle
     */
    public Rectangle(){
        super();
        this.setType(Type.RECTANGLE);
        //this.setWidth(0);
    }

    /**
     * Override constructor that sets the width and height of the rectangle
     * @param width float that represents the width of a rectangle
     * @param height float that represents the height of a rectangle
     */
    public Rectangle(float width, float height){
        super();
        this.setType(Type.RECTANGLE);
        this.setWidth(width);
        this.height = height;
    }

    /**
     * Gets the height of a rectangle
     * @return height as a float
     */
    public float getHeight(){
        return this.height;
    }

    /**
     * Sets the height of the rectangle
     * @param height float that represents the height of a rectangle
     */
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
        return String.format("%s Height: %-10.2f ",super.toString(), this.getHeight());
    }
}
