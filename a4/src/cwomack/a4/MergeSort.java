package cwomack.a4;

import cwomack.a3.Shape;

public class MergeSort extends Thread{
    private Shape[] shapes1;
    private Shape[] shapes2;
    private Shape[] sortedShapes;

    public MergeSort(Shape[] s1, Shape[] s2){
        this.shapes1 = s1;
        this.shapes2 = s2;
        this.sortedShapes = new Shape[s1.length + s2.length];
    }
    
    @Override
    public void run(){
        System.out.println("Merge Thread Started");
        int i=0; //Current index of shapes1
        int j=0; //Current index of shapes2
        int k=0; //Current index of sorted Shapes

        while(i<shapes1.length && j<shapes2.length){
            if(this.shapes1[i].getArea() < this.shapes2[j].getArea()){
                this.sortedShapes[k++] = this.shapes1[i++];
            } else {
                this.sortedShapes[k++] = this.shapes2[j++];
            }
        }
        while(i < this.shapes1.length){
            this.sortedShapes[k++] = this.shapes1[i++];
        }
        while(j < this.shapes2.length){
            this.sortedShapes[k++] = this.shapes2[j++];
        }
        System.out.println("Merge Thread Complete");
    }

    public Shape[] getSortedShapes(){
        return sortedShapes;
    }
}
