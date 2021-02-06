package cwomack.a3;

/**
 * Oval inherits radius from the Circle class and sets its own second radius
 * @author Carter Womack
 * @version 1.0
 */
public class Oval extends Circle{
    private float radius2;

    public Oval(){
        super();
        this.setType(Type.OVAL);
        this.setRadius(0);
    }
    public Oval(float radius, float radius2) {
        super();
        this.setRadius(radius);
        this.radius2 = radius2;
    }
    public float getRadius2(){
        return this.radius2;
    }
    public void setRadius2(float radius2){
        this.radius2 = radius2 >= 0 ? radius2 : 0;
    }

    @Override
    public double getArea(){
        return this.getRadius() * this.getRadius2() * Math.PI;
    }
    @Override
    public String toString(){
        return String.format("%s Radius: %-10.2f", super.toString(), this.getRadius(),
        "%s Radius2: %-10.2f", super.toString(), this.getRadius2());
    }
}
