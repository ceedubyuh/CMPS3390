package cwomack.a4;
import cwomack.a3.Shape;
public class ThreadSort extends Thread {
    private Shape[] tShapes;

    public ThreadSort(Shape[] shapes, int lowBounds, int upperBounds){
        this.tShapes = new Shape[upperBounds - lowBounds];

        System.arraycopy(shapes, lowBounds, this.tShapes, 0, (upperBounds - lowBounds));
    }

    @Override
    public void run(){
        System.out.println("Thread started");
        int n = this.tShapes.length;
        Shape tmp;
        for(int i = 0; i<n; i++){
            for(int j=1; j<n; j++){
                if(this.tShapes[j-1].getArea() > this.tShapes[j].getArea()){
                    tmp = this.tShapes[j-1];
                    this.tShapes[j-1] = this.tShapes[j];
                    this.tShapes[j] = tmp;
                }
            }
        }
        System.out.println("Thread complete");
    }

    public Shape[] gettShapes() {
        return tShapes;
    }
}
