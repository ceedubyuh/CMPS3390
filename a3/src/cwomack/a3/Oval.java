package cwomack.a3;

/**
 * Oval extends Circle and adds radius2 to create an oval and find its measurements
 * @author Carter Womack
 * @version 1.0
 */
public class Oval extends Circle{
    private float radius2;

    /**
     * Default constructor to set the type of shape to Oval
     */
    public Oval(){
        super();
        this.setType(Type.OVAL);
        this.setRadius(0);
    }

    /**
     * Override constructor that sets radius and radius 2
     * @param radius float that represents radius 1
     * @param radius2 float that represents radius 2
     */
    public Oval(float radius, float radius2) {
        super();
        this.setType(Type.OVAL);
        this.setRadius(radius);
        this.radius2 = radius2;
    }

    /**
     * Grabs radius 2 of the oval
     * @return radius 2
     */
    public float getRadius2(){
        return this.radius2;
    }

    /**
     * Sets radius 2 to a number greater than or equal to 0
     * @param radius2 float that represents radius2
     */
    public void setRadius2(float radius2){
        this.radius2 = radius2 >= 0 ? radius2 : 0;
    }

    @Override
    /**
     * Override Circle's parent getArea to find the area of an Oval
     */
    public double getArea(){
        return this.getRadius() * this.getRadius2() * Math.PI;
    }
    @Override
    /**
     * Override of Circle's parent toString to add radius 2
     */
    public String toString(){
        return String.format("%s Radius2: %-10.2f", super.toString(), this.getRadius2());
    }
}
