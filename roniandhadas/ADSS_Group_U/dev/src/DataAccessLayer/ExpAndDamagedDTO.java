package DataAccessLayer;

public class ExpAndDamagedDTO {

    private ExpAndDamagedMapper expAndDamagedMapper;
    private Boolean isPersisted;
    private String type;
    private String store;
    private String category;
    private String subcategory;
    private int serialNumber;
    private int amount;


    public ExpAndDamagedDTO(String type, String store, String category, String subcategory, int serialNumber, int amount){
        this.expAndDamagedMapper=new ExpAndDamagedMapper();
        this.isPersisted=false;
        this.type=type;
        this.store=store;
        this.category=category;
        this.subcategory=subcategory;
        this.serialNumber=serialNumber;
        this.amount=amount;
    }

    public String getType(){
        return this.type;
    }

    public String getStore(){
        return this.store;
    }

    public String getCategory(){
        return this.category;
    }

    public String getSubcategory(){
        return this.subcategory;
    }

    public int getSerialNumber(){
        return this.serialNumber;
    }

    public int getAmount(){ return this.amount; }

    public void increaseAmount(int count) throws Exception {
        try {
            expAndDamagedMapper.increaseAmount(type, store, serialNumber, count);
        } catch (Exception e) {
            throw new Exception("didn't increase amount");
        }
    }

    public void remove() throws Exception {
        try {
            expAndDamagedMapper.removeProCategory(type, store, category, serialNumber);
        } catch (Exception e) {
            throw new Exception("didn't remove");
        }
    }

    public void insert() throws Exception {
        try {
            expAndDamagedMapper.insert(this);
        } catch (Exception e) {
            throw new Exception("didn't add");
        }
    }


}
