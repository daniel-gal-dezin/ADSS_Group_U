package DataAccessLayer;

import DomainLayer.ProductCounter;

public class ProductDTO {
    private ProductsMapper productsMapper;
    private Boolean isPersisted;
    private int productId;
    private String store;
    private String category;
    private String subcategory;
    private int serialNumber;
    private String name;
    private int stock;
    private int shelf;
    private int aigle;
    private String producer;
    private int cost;
    private int soldPrice;
    private int size;
    private int discount;
    private int minimumAmount;

    public ProductDTO(String store, String category, String subcategory, int serialNumber, String name, int stock, int shelf, int aigle, String producer, int cost, int soldPrice, int size, int discount, int minimumAmount){
        this.productsMapper=new ProductsMapper();
        this.isPersisted=false;
        ProductCounter counter=ProductCounter.getInstance();
        this.productId=counter.getCount();
        this.store=store;
        this.category=category;
        this.subcategory=subcategory;
        this.serialNumber=serialNumber;
        this.name=name;
        this.stock=stock;
        this.shelf=shelf;
        this.aigle=aigle;
        this.producer=producer;
        this.cost=cost;
        this.soldPrice=soldPrice;
        this.size=size;
        this.discount=discount;
        this.minimumAmount=minimumAmount;
    }

    public String getStore(){
        return store;
    }

    public String getCategory(){
        return category;
    }

    public String getSubcategory(){
        return subcategory;
    }

    public int getSerialNumber(){
        return serialNumber;
    }

    public String getName(){
        return name;
    }

    public int getStock(){
        return stock;
    }

    public int getShelfNum(){
        return shelf;
    }

    public int getAigleNum(){
        return aigle;
    }

    public String getProducer(){
        return producer;
    }

    public int getCost(){
        return cost;
    }

    public int getSoldPrice(){
        return soldPrice;
    }

    public int getSize(){
        return size;
    }

    public int getDiscount(){
        return discount;
    }

    public int getMinimumAmount(){
        return minimumAmount;
    }

    public int getProductId(){
        return productId;
    }

    public void setPersist(){
        this.isPersisted=true;
    }

    public void addItem(ItemDTO item) throws Exception {
        item.persist();
        this.stock=stock+1;
        productsMapper.increaseStock(productId);
    }

    public void persist() throws Exception {
        try {
            productsMapper.insert(this);
            isPersisted = true;
        } catch (Exception e) {
            throw new Exception("didn't add to the database");
        }
    }

    public void updateMinimumAmount(int amount) throws Exception {
        try {
            productsMapper.updateMinimumAmount(productId, amount);
        } catch (Exception e) {
            throw new Exception("didn't update minimum");
        }
    }

    public void removePro() throws Exception {
        try {
            productsMapper.removePro(productId);
            isPersisted=false;
        } catch (Exception e) {
            throw new Exception("didn't remove from database");
        }
    }

    public void decreaseStock() throws Exception {
        try {
            productsMapper.decreaseStock(productId);
        } catch (Exception e) {
            throw new Exception("didn't change stock");
        }
    }

    public void updateDiscount(int discount) throws Exception {
        try {
            productsMapper.updateDiscount(productId, discount);
        } catch (Exception e) {
            throw new Exception("didn't update discount");
        }
    }


}
