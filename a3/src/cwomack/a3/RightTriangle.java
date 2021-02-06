package cwomack.a3;
/**
 * Right Triangle class for A3
 * @author Carter Womack
 * @version 1.0
 */
public class RightTriangle extends Rectangle{
    private float height;

    public RightTriangle(){
        super();
        this.setType(Type.RightTriangle);
        this.setHeight(0);
    }
    /**
     * @param width width of the right triangle, inherited from the rectangle class
     * @param height height of the right triangle
     */
    public RightTriangle(float width, float height){
        super();
        this.setType(Type.RightTriangle);
        this.setWidth(width);
        this.setHeight(height);
    }

    /**
     * Override getHeight from inherited parent class
     * @return height of the triangle
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
