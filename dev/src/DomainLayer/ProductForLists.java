package DomainLayer;

public class ProductForLists {
    public Product product;
    public String output;

    public ProductForLists(String category, String subcategory, Product p){
        product=p;
        output="Category: "+category+" Sub-category: "+subcategory+" Name: "+p.getName()+" Serial Number: "+p.getSerialNum()+" Amount missing: "+p.stockWarning();
    }

    public String getOutput(){
        return output;
    }

}
