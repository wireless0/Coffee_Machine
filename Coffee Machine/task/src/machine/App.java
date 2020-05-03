package machine;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        CoffeeMachine coffeeMachine = new CoffeeMachine(550, 400, 540, 120, 9);

        while(true) {
            System.out.println(coffeeMachine.getPrompt());
            coffeeMachine.operateCoffeeMachine(scanner.next());
        }
    }
}
