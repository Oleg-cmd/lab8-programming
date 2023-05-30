package client.helpers;

import java.io.OutputStream;
import java.io.PrintStream;

public class SymbolicPrintStream extends PrintStream {
    private String symbol;
    private String[] colors;
    private int colorIndex;

    public SymbolicPrintStream(String symbol, String[] colors, OutputStream out) {
        super(out);
        this.symbol = symbol;
        this.colors = colors;
        this.colorIndex = 0;
    }

    @Override
    public void println(String x) {
        String[] lines = x.split("\n");
        for (String line : lines) {
            super.println("\033[" + colors[colorIndex] + "m" + symbol + " \033[0m" + line);
            colorIndex = (colorIndex + 1) % colors.length;
        }
    }
}
