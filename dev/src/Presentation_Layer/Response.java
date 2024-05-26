package Presentation_Layer;

public class Response {

    public String ErrorMessage;
    public String ReturnValue;

    public Response(String str) throws Exception {
        String[] s = str.split(":");
        if(s.length != 2)
            throw new Exception("something went wring with response! got " + str);
        ErrorMessage = s[0];
        ReturnValue = s[1];
    }

    public boolean isError(){
        return !ErrorMessage.equals("");
    }

    public String getErrorMessage(){
        return this.ErrorMessage;
    }

    public String getReturnValue(){
        return this.ReturnValue;
    }
}
