package modules;

import helpers.CheckName;
import helpers.UserInputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import request.ReadFromClient;
import request.SendCommandList;

import java.io.IOException;

public class Process {
    private static final Logger logger = LogManager.getLogger(Process.class);
    public static void Process(String[] input, String xmlData) throws IOException {
        if(CheckName.CheckName(input[0])){
            logger.info("Command is ok");
            logger.info("Starting executing command");
            UserInputHandler.toExecute(null, input, xmlData);

         }else{
            logger.error("problems with command");
        }
    }
}


