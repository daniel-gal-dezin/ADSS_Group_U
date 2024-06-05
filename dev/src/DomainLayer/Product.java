package DomainLayer;
import java.util.Date;
import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Product {
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

    public Product(String name, int serialNum, int aigleNum, int shelfNum, String producer, int cost, int soldPrice, int size){ //implement places
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

    public boolean addItem(int id, String expDate){
        if(itemListInStorage.get(id)!=null || itemListInStore.get(id)!=null)
            return false;
        Item newItem=new Item(id, expDate);
        itemListInStorage.put(id, newItem);
        this.stock=this.stock+1;
        return true;
    }

    public Item removeItem(int id){
        this.stock=this.stock-1;
        Item it1=itemListInStorage.remove(id);
        if (it1==null){
            return itemListInStore.remove(id);
        }
        return it1;
    }

    public boolean setDiscount(int discount){
        this.discount=discount;
        return true;
    }

    public double getProductPrice(){
        return ((double)(100-discount)/100)*soldPrice;
    }

    public boolean moveToStore(int id){
        Item it=itemListInStorage.remove(id);
        if (it!=null){
            itemListInStore.put(id, it);
            it.setPlace("store");
            return true;
        }
        return false;
    }
}
