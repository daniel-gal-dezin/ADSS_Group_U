package ServiceLayer;

public class Response {
    public String ErrorMessage;
    public String ReturnValue;

    public Response()
    {
        ErrorMessage = "";
        ReturnValue = "";
    }

    public Response(String ErrorMsg)
    {
        ErrorMessage = ErrorMsg;
        ReturnValue = "";
    }

    public Response(String ErrorMsg, String retVal)
    {
        ErrorMessage = ErrorMsg;
        ReturnValue = retVal;
    }

    public String toJson(){
        return ErrorMessage + ReturnValue;
    }
}