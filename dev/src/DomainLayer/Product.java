package DomainLayer;
import DataAccessLayer.ItemDTO;
import DataAccessLayer.ProductDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Product {
    private String store;
    private String category;
    private String subcategory;
    private String name;
    private int serialNum;
    private int stock;
    private Place inStoreAndInStorage;
    private String producer;
    private int cost;
    private int soldPrice;
    private int size;
    private int discount;
    private Map<Integer, Item> itemListInStorage;
    private Map<Integer, Item> itemListInStore;
    private int minimumAmount;
    private ProductDTO pdto;

    public Product(String store, String category, String subcategory, String name, int serialNum, int aigleNum, int shelfNum, String producer, int cost, int soldPrice, int size){
        this.store=store;
        this.category=category;
        this.subcategory=subcategory;
        this.name=name;
        this.serialNum=serialNum;
        this.stock=0;
        this.inStoreAndInStorage=new Place(aigleNum, shelfNum);
        this.producer=producer;
        this.cost=cost;
        this.soldPrice=soldPrice;
        this.size=size;
        this.discount=0; //precentage
        itemListInStorage=new LinkedHashMap<Integer, Item>();
        itemListInStore=new LinkedHashMap<Integer, Item>();
        this.minimumAmount=-1;
        this.pdto=new ProductDTO(store, category, subcategory, serialNum, name, 0, shelfNum, aigleNum, producer, cost, soldPrice, size, 0, -1);
    }

    public Product(ProductDTO pdto){
        this.store=pdto.getStore();
        this.category=pdto.getCategory();
        this.subcategory=pdto.getSubcategory();
        this.name=pdto.getName();
        this.serialNum=pdto.getSerialNumber();
        this.stock=pdto.getStock();
        this.inStoreAndInStorage=new Place(pdto.getAigleNum(), pdto.getShelfNum());
        this.producer= pdto.getProducer();
        this.cost= pdto.getCost();
        this.soldPrice= pdto.getSoldPrice();
        this.size=pdto.getSize();
        this.discount= pdto.getDiscount(); //precentage
        itemListInStorage=new LinkedHashMap<Integer, Item>();
        itemListInStore=new LinkedHashMap<Integer, Item>();
        this.minimumAmount=pdto.getMinimumAmount();
        this.pdto=pdto;
        this.pdto.setPersist();
    }

    public int removeExpItems() throws Exception {
        int count=0;
        for (Item it: itemListInStorage.values()){
            if (it.getExpDate().isBefore(LocalDate.now())){
                removeItem(it.getId());
                count=count+1;
            }
        }
        for (Item it: itemListInStore.values()){
            if (it.getExpDate().isBefore(LocalDate.now())){
                removeItem(it.getId());
                count=count+1;
            }
        }
        return count;
     }


    public String getName(){
        return this.name;
    }

    public int getSerialNum(){
        return this.serialNum;
    }

    public int getStock(){
        return this.stock;
    }

    public String getPlace(){
        return inStoreAndInStorage.getStringPlace();
    }

    public String getProducer(){
        return this.producer;
    }

    public int getSize(){
        return this.size;
    }

    public Map<Integer, Item> itemListInStorage(){
        return this.itemListInStorage;
    }

    public Map<Integer, Item> itemListInStore(){
        return this.itemListInStore;
    }

    public boolean addItem(int id, String expDate) throws Exception {
        if(itemListInStorage.get(id)!=null || itemListInStore.get(id)!=null)
            return false;
        Item newItem=new Item(pdto.getProductId(), id, expDate);
        itemListInStorage.put(id, newItem);
        this.stock=this.stock+1;
        try
        {
            pdto.addItem(newItem.getIdto());
        }
        catch (Exception ex)
        {
            itemListInStorage.remove(id);
            throw new Exception (ex.getMessage());
        }
        return true;
    }

    public Item removeItem(int id) throws Exception {
        this.stock=this.stock-1;
        Item it1=itemListInStorage.remove(id);
        if (it1==null){
            it1=itemListInStore.remove(id);
        }
        it1.getIdto().removeItem();
        return it1;
    }

    public void addItemFromDB(ItemDTO i){
        if (!itemListInStorage.containsKey(i.getId()) && !itemListInStore.containsKey(i.getId())){
            if (i.getPlace().equals("storage")){
                itemListInStorage.put(i.getId(), new Item(i));
            }
            else {
                itemListInStore.put(i.getId(), new Item(i));
            }
        }
    }

    public int stockWarning(){
        if(stock<minimumAmount){
            return minimumAmount-stock;
        }
        return -1;
    }

    public boolean updateMinimumAmount(int amount) throws Exception {
        this.minimumAmount=amount;
        this.pdto.updateMinimumAmount(amount);
        return true;
    }

    public boolean setDiscount(int discount) throws Exception {
        this.discount=discount;
        this.pdto.updateDiscount(discount);
        return true;
    }

    public double getProductPrice(){
        return ((double)(100-discount)/100)*soldPrice;
    }

    public boolean moveToStore(int id) throws Exception {
        Item it=itemListInStorage.remove(id);
        if (it!=null){
            itemListInStore.put(id, it);
            it.setPlace("store");
            return true;
        }
        return false;
    }

    public ProductDTO getPdto(){
        return this.pdto;
    }
}
