import db.DatabaseManager;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Stream.of("mercury", "venus", "earth", "mars", "jupiter", "saturn", "uranus", "neptune")
                .filter(s -> s.length() != 7).map(Main::swapEdges).limit(3).sorted()
                .forEachOrdered(System.out::println);

    }

    public static String swapEdges(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        char[] chars = s.toCharArray();
        char first = chars[0];
        chars[0] = chars[chars.length - 1];
        chars[chars.length - 1] = first;
        return new String(chars);
    }
}
