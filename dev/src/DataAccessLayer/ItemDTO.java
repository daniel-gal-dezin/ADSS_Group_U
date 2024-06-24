package DataAccessLayer;

public class ItemDTO {

    private ItemsMapper itemsMapper;
    private boolean isPersisted;
    private int productId;
    private int id;
    private String expDate;
    private String place;

    public ItemDTO(int productId, int id, String expDate, String place){
        this.itemsMapper=new ItemsMapper();
        this.isPersisted=false;
        this.productId=productId;
        this.id=id;
        this.expDate=expDate;
        this.place=place;
    }

    public int getProductId(){
        return this.productId;
    }

    public int getId(){
        return this.id;
    }

    public String getExpDate(){
        return this.expDate;
    }

    public String getPlace(){
        return this.place;
    }

    public void setPersist(){
        this.isPersisted=true;
    }

    public void persist() throws Exception {
        try {
            itemsMapper.insert(this);
            isPersisted = true;
        } catch (Exception e) {
            throw new Exception("didn't add to the database");
        }
    }

    public void removeItem() throws Exception {
        try {
            itemsMapper.removeIt(productId, id);
            isPersisted = false;
        } catch (Exception e) {
            throw new Exception("didn't add to the database");
        }
    }

    public void updatePlace(String place) throws Exception {
        try {
            itemsMapper.updatePlace(productId, id, place);
        } catch (Exception e) {
            throw new Exception("didn't update place");
        }
    }
}
