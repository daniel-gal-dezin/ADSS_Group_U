package DomainLayer;

public class ProductCounter {
    private int count;
    private static ProductCounter instance=null;

    public ProductCounter(){
        this.count=0;
    }

    public static ProductCounter getInstance(){
        if (instance==null){
            instance=new ProductCounter();
        }
        return instance;
    }

    public int getCount(){
        this.count=this.count+1;
        return this.count;
    }
}
