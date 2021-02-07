package cwomack.a3;
/**
 * Right Triangle class for A3
 * @author Carter Womack
 * @version 1.0
 */
public class RightTriangle extends Rectangle{
    private float height;

    /**
     * Default constructor for RightTriangle class that sets the type and height
     */
    public RightTriangle(){
        super();
        this.setType(Type.RightTriangle);
        this.setHeight(0);
    }
    /**
     * Override constructor that sets type, width and height
     * @param width float that represents the width of a right triangle
     * @param height float that represents the height of a right triangle
     */
    public RightTriangle(float width, float height){
        super();
        this.setType(Type.RightTriangle);
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * Override getHeight from inherited parent class
     * @return height of the triangle as a float
     */
    @Override
    public float getHeight(){
        return this.height;
    }
    /**
     * Override setHeight from inherited parent class
     */
    @Override
    public void setHeight(float height){
        this.height = Math.max(0, height);
    }
    /**
     * Override getArea from inherited parent class
     * @return width multiplied by the height, divided by 2, which gives the area of a right triangle.
     */
    @Override
    public double getArea(){
        return ((this.getWidth() * this.getHeight() / 2));
    }
}
