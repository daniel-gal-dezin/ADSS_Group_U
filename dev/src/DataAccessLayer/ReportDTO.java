package DataAccessLayer;

public class ReportDTO {

    private ReportsMapper reportsMapper;
    private String store;
    private String type;
    private String report;

    public ReportDTO(String store, String type, String report){
        this.reportsMapper=new ReportsMapper();
        this.store=store;
        this.type=type;
        this.report=report;
    }


    public String getStore(){
        return this.store;
    }

    public String getType(){
        return this.type;
    }

    public String getReport(){
        return this.report;
    }

    public void addReport() throws Exception {
        try {
            reportsMapper.insert(this);
        } catch (Exception e) {
            throw new Exception("didn't add to the database");
        }
    }
}
