package Presentation_Layer;

public class Response {

    public String ErrorMessage;
    public String ReturnValue;

    public Response(String str){
        System.out.println(str);
        if(str.equals("~~")){
            ErrorMessage = "";
            ReturnValue = "";
            return;
        }
        if(str.charAt(0) == '~'){
            ErrorMessage = "";
            ReturnValue = str.substring(2,str.length() - 1);
            return;
        }
        else if(str.charAt(str.length()-1) == '~' ){
            ErrorMessage = str.substring(0,str.length() - 3);
            ReturnValue = "";
            return;
        }
        if(!str.contains("~~")){

            ErrorMessage = "JSON in bad format";
            return;
        }
        String[] s = str.split("~");
        if(s.length > 2){
            ErrorMessage = "JSON in bad format";
        }
        else {
            ErrorMessage = s[0];
            ReturnValue = s[1];
        }
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

    public void print(){
        if(isError()){
            System.out.println("Error: " + ErrorMessage);
        }
        else if(ReturnValue == ""){
            System.out.println("Successful action!");
        }
        else{
            System.out.println("Success. returned value: " + ReturnValue);
        }
    }
}
