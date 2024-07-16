package Domain_Layer;

import java.util.Locale;

public enum Role {
    MANAGER,
    STOREKEEPER,
    CASHIER,
    DRIVER;




    private Role convertRole(String role){
        if(role.toLowerCase() == "Manager")
            return Role.MANAGER;
        else if(role.toLowerCase() == "Storekeeper")
            return Role.STOREKEEPER;
        else if((role.toLowerCase() == "Cashier"))
            return Role.CASHIER;
        else if ((role.toLowerCase() == "Driver"))
            return Role.DRIVER;
        else
            throw new IllegalArgumentException("Could't add role '" + role + "'. does not exist!");

    }
}
