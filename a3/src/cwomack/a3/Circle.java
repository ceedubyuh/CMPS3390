package cwomack.a3;

/**
 * Circle class for a3
 * This class extends Shape sets the radius and area of a perfect circle
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

    /**
     * OVerride constructor to set the type of shape to circle and radius
     * @param radius float that represents the radius of the circle
     */
    public Circle(float radius){
        super();
        this.setType(Type.CIRCLE);
        this.radius = radius;
    }

    /**
     * Grabs radius of the circle
     * @return float radius
     */
    public float getRadius(){
        return this.radius;
    }

    /**
     * Sets the radius of a circle an integer and checks if it is greater than or equal to 0
     * @param radius float that represents the radius of the circle
     */
    public void setRadius(float radius){
        this.radius = radius >= 0 ? radius : 0;
    }

    /**
     * Function to calculate the area of a circle
     * @return calculated area
     */
    public double getArea(){
        return Math.PI * this.radius * this.radius;
    }

    /**
     * Override of Shape's toString to add radius
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s Radius: %-10.2f", super.toString(), this.getRadius());
    }
}
