package helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class CommandObject implements Serializable {

    private final String commandName;
    private final String[] tokens;
    private String xmlData;

    public CommandObject(String commandName, String[] tokens, String XML) {
        this.commandName = commandName;
        this.tokens = tokens;
        this.xmlData = XML;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getTokens() {
        return tokens;
    }

    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        return bos.toByteArray();
    }

    @Override
    public String toString() {
        return "CommandObject{" +
                "commandName='" + commandName + '\'' +
                ", tokens=" + Arrays.toString(tokens) +
                ", xmlData=" + (xmlData == null ? "null" : xmlData) +
                '}';
    }
}
