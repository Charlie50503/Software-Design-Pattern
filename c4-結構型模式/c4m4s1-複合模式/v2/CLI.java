package v2;

import java.util.List;
import java.util.Scanner;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class CLI {
    private final Scanner in = new Scanner(System.in);
    private Directory current;  // 當前目錄

    public CLI(Directory current) {
        this.current = current;
    }

    public void start() {
        while (true) {
            System.out.printf("%s> ", current.getName());
            String command = in.nextLine();
            executeCommand(command);
        }
    }

    private void executeCommand(String command) {
        String[] parts = command.split(" ");
        if ("cd".equals(parts[0])) {
            changDirectory(parts[1]);
        } else if ("size".equals(parts[0])) {
            size(parts[1]);
        } else if ("search".equals(parts[0])) {
            search(parts[0]);
        } else {
            System.err.println("Unrecognizable Command.");
        }
    }

    private void size(String name) {
        current.getItem(name)
                .map(Item::totalBytes)
                .ifPresentOrElse(size -> System.out.printf("Size: %dB%n", size),
                        () -> System.err.printf("Can't find the item '%s'.%n", name));
    }

    private void search(String keyword) {
        List<Item> items = current.search(keyword);
        System.out.printf("Count: %d%n", items.size());
    }

    private void changDirectory(String name) {
        if ("..".equals(name)) {
            current = current.getParent() == null /*root*/ ?
                    current : current.getParent();
        } else {
            Directory target = (Directory) current.getItem(name);
            if (target == null) {
                System.err.printf("Can't find the item '%s'.%n", name);
            } else {
                current = target;
            }
        }
    }
}
