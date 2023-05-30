package helpers;

import collectionWorker.HelpCommand;


public class CheckName {
    public static boolean CheckName(String name){
//        System.out.println("Provided command name:  " + name.trim());
        String[] instructions = HelpCommand.myFuncsName;
        for(String n: instructions){
            if (n.equals(name.trim())){
                return true;
            }

        }
        return false;
    }
}
