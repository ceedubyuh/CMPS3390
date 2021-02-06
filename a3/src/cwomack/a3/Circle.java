package cwomack.a3;

/**
 * Circle class for a3
 * This class sets the radius and area of each circle that is passed through it
 * @author Carter Womack
 * @version 1.0
 */
public class Circle extends Shape{
    private float radius;

    /**
     * Default constructor for the circle
     */
    public Circle() {
        super();
        this.setType(Type.CIRCLE);
    }
    public Circle(float radius){
        super();
        this.radius = radius;
    }
    public float getRadius(){
        return this.radius;
    }
    public void setRadius(float radius){
        this.radius = radius >= 0 ? radius : 0;
    }
    public double getArea(){
        return Math.PI * this.radius * this.radius;
    }

    @Override
    public String toString() {
        return String.format("%s Radius: %-10.2f", super.toString(), this.getRadius());
    }
}
