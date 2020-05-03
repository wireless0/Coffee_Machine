package machine;
import java.util.Scanner;

public class CoffeeMachine {

    private int money;
    private int water;
    private int milk;
    private int beans;
    private int cups;

    public String getPrompt() {
        return prompt;
    }

    private String prompt = "Write action (buy, fill, take, remaining, exit):";

    enum State {
        IDLE, BUYING, FILLING
    }

    enum FillingState {
        WATTER, MILK, BEANS, CUPS
    }

    private State state = State.IDLE;

    private FillingState fillingState = FillingState.WATTER;

    enum CoffeeType {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        private final int water;
        private final int milk;
        private final int beans;
        private final int money;

        CoffeeType(int water, int milk, int beans, int money) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.money = money;
        }
    }

    public CoffeeMachine(int money, int water, int milk, int beans, int cups) {
        this.money = money;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
    }

    private String buy(CoffeeType choice) {
        CoffeeType chosenType;
        switch (choice) {
            case ESPRESSO:
                chosenType = CoffeeType.ESPRESSO;
                break;
            case LATTE:
                chosenType = CoffeeType.LATTE;
                break;
            case CAPPUCCINO:
                chosenType = CoffeeType.CAPPUCCINO;
                break;
            default:
                return "";
        }
        if (chosenType.water > water) {
            return "Sorry, not enough water!\n";
        }
        if (chosenType.milk > milk) {
            return "Sorry, not enough milk!\n";
        }
        if (chosenType.beans > beans) {
            return "Sorry, not enough coffee beans!\n";
        }
        if (cups == 0) {
            return "Sorry, not enough cups!\n";
        }
        money += chosenType.money;
        water -= chosenType.water;
        milk -= chosenType.milk;
        beans -= chosenType.beans;
        cups--;
        return "I have enough resources, making you a coffee!\n";
    }

    private void processFilling(String input) {
        switch (fillingState) {
            case WATTER:
                try {
                    water += Integer.parseInt(input);
                } catch (NumberFormatException ignored) {}
                prompt = "Write how many ml of milk do you want to add:";
                fillingState = FillingState.MILK;
                break;
            case MILK:
                try {
                    milk += Integer.parseInt(input);
                } catch (NumberFormatException ignored) {}
                prompt = "Write how many grams of coffee beans do you want to add:";
                fillingState = FillingState.BEANS;
                break;
            case BEANS:
                try {
                    beans += Integer.parseInt(input);
                } catch (NumberFormatException ignored) {}
                prompt = "Write how many disposable cups of coffee do you want to add:";
                fillingState = FillingState.CUPS;
                break;
            case CUPS:
                try {
                    cups += Integer.parseInt(input);
                } catch (NumberFormatException ignored) {}
                prompt = "Write action (buy, fill, take, remaining, exit):";
                fillingState = null;
                state = State.IDLE;
                break;
            default:
                break;
        }
    }

    private void processBuying(String input) {
        String response = "";
        switch (input) {
            case "1":
                response = buy(CoffeeType.ESPRESSO);
                break;
            case "2":
                response = buy(CoffeeType.LATTE);
                break;
            case "3":
                response = buy(CoffeeType.CAPPUCCINO);
                break;
            case "back":
                break;
            default:
                response = "No such drink\n";
                break;
        }
        state = State.IDLE;
        prompt = response + "Write action (buy, fill, take, remaining, exit):";
    }

    private void processIdle(String input) {
        switch (input) {
            case "buy":
                prompt = "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
                state = State.BUYING;
                break;
            case "fill":
                prompt = "Write how many ml of water do you want to add:";
                state = State.FILLING;
                fillingState = FillingState.WATTER;
                break;
            case "take":
                prompt = "I gave you $" + money + "\nWrite action (buy, fill, take, remaining, exit):";
                money = 0;
                break;
            case "remaining":
                prompt =    "The coffee machine has:\n" +
                            water + " of water\n" +
                            milk + " of milk\n" +
                            beans + " of coffee beans\n" +
                            cups + " of disposable cups\n" +
                            money + " of money\n" +
                            "Write action (buy, fill, take, remaining, exit):";
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                prompt = "Invalid input.\nWrite action (buy, fill, take, remaining, exit):";
                break;
        }
    }

    public void operateCoffeeMachine(String input) {
        switch (state) {
            case IDLE:
                processIdle(input);
                break;
            case BUYING:
                processBuying(input);
                break;
            case FILLING:
                processFilling(input);
                break;
        }
    }

}
