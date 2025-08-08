
/**
 * An interactive console app to teach energy-saving habits for driving and air conditioning
 * @Rohan Brahnmath, 
 * @v.1.0
 */

import java.util.*;

public class EnergyConservationApp {
    private static class InputCancelledException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
     private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, Integer> conservationEfforts = new HashMap<>();

    // Quiz questions and answers
    private static String[][] quizQuestions = {
        {"Which action can save fuel while driving?", "Accelerate gently", "Drive fast", "Idle your car", "Use cruise control", "1,4"},
        {"What thermostat setting is energy efficient?", "68°F", "78°F", "85°F", "60°F", "2"},
        {"Why should AC filters be cleaned?", "To improve efficiency", "To look nice", "To cool faster", "To make noise", "1"},
        {"What helps reduce car energy use?", "Underinflated tires", "Proper tire inflation", "Removing extra weight", "Idling engine", "2,3"},
        {"What is a simple way to keep your home cooler without lowering the thermostat?", "Open windows during the hottest part of the day", "Close curtains or blinds to block sunlight", "Run the oven during peak heat", "Turn on all the lights", "2"},
        {"What is the best time to use major appliances like washing machines to save energy?", "During peak afternoon hours", "Whenever it’s convenient", "Late at night or early morning", "During the hottest part of the day", "3"},
        {"How can you reduce short trips fuel use?", "Combine errands", "Drive aggressively", "Keep engine running", "Drive slowly in wrong lane", "1"},
        {"Why use fans along with AC?", "To heat the room", "To waste energy", "To make noise", "To circulate air", "4"},
        {"What happens when you idle instead of turning engine off?", "Wastes fuel", "Saves fuel", "Reduces emissions", "None", "1"},
        {"What kind of thermostat helps save energy?", "Manual", "Programmable", "Broken one", "Any kind", "2"}
        
    };

    public static void main(String[] args) {
        clearConsole();
        printWelcome();
        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    printDrivingTips();
                    break;
                case "2":
                    printACTips();
                    break;
                case "3":
                    printWhyConserve();
                    break;
                case "4":
                    runQuiz();
                    break;
                case "5":
                    trackEfforts();
                    break;
                case "6":
                    showEffortsSummary();
                    break;
                case "7":
                    printLocalResources();
                    break;
                case "8": 
                    runVirtualAssistant();
                    break;
                case "0":
                    printFarewell();
                    scanner.close();
                    return;
                default:
                    System.out.println("Please enter a valid choice.\n");
            }
            System.out.println("---------------------------------------\n");
        }
    }

    private static void printWelcome() {
        System.out.println("=======================================");
        System.out.println("   Welcome to Energy Conservation App  ");
        System.out.println("=======================================");
        System.out.println("What would you like to learn or do today?\n");
    }

    private static void printMenu() {
        System.out.println("Choose an option:");
        System.out.println("1 - Driving: Save Energy");
        System.out.println("2 - Air Conditioning: Save Energy");
        System.out.println("3 - Why Conserve Energy?");
        System.out.println("4 - Energy Quiz");
        System.out.println("5 - Track Conservation Efforts");
        System.out.println("6 - Show My Efforts Summary");
        System.out.println("7 - Local Energy-Saving Resources");
        System.out.println("8 - Energy Coach");
        System.out.println("0 - Exit");
        System.out.print("Your choice: ");
    }

    private static void printDrivingTips() {
        System.out.println("\n🌱 Driving Energy Conservation Tips:");
        System.out.println("- Accelerate and brake gently to reduce fuel use.");
        System.out.println("- Use cruise control for steady speeds when safe.");
        System.out.println("- Keep tires properly inflated and get regular tune-ups.");
        System.out.println("- Remove unneeded items from your car to reduce weight.");
        System.out.println("- Avoid idling for long periods. Turn off your engine when parked.");
        System.out.println("- Plan trips to combine errands and avoid traffic when possible.\n");
        askReturnOrExit();
    }

    private static void printACTips() {
        System.out.println("\n❄️ Air Conditioning Energy Conservation Tips:");
        System.out.println("- Set your thermostat to 78°F (25°C) when at home.");
        System.out.println("- Use ceiling or portable fans to boost cool air circulation.");
        System.out.println("- Keep windows and doors closed; draw blinds during peak heat.");
        System.out.println("- Clean or replace AC filters for maximum efficiency.");
        System.out.println("- Schedule regular maintenance checks for your unit.");
        System.out.println("- Upgrade to an energy-efficient model and use programmable thermostats.\n");
        askReturnOrExit();
    }

    private static void printWhyConserve() {
        System.out.println("\n💡 Why Save Energy?");
        System.out.println("- Reduces greenhouse gas emissions and slows climate change.");
        System.out.println("- Saves money on fuel and electricity bills.");
        System.out.println("- Helps create a healthier environment for everyone.");
        System.out.println("- Small actions add up to big impacts!\n");
        askReturnOrExit();
    }

    private static void runQuiz() {
    System.out.println("Type 'q' at any time to cancel and return to the main menu.");

    try {
        String retry;
        do {
            System.out.println("\n📚 Energy Conservation Quiz:");
            int score = 0;

            for (int i = 0; i < quizQuestions.length; i++) {
                System.out.println("\n" + (i + 1) + ". " + quizQuestions[i][0]);
                for (int j = 1; j <= 4; j++) {
                    System.out.println("  " + j + ". " + quizQuestions[i][j]);
                }

                String[] answers;
                while (true) {
                    String input = getInput("Enter your answer(s) separated by commas (or 'q' to quit): ");
                    input = input.replaceAll("\\s+", "");
                    answers = input.split(",");

                    boolean valid = true;
                    for (String ans : answers) {
                        if (!ans.matches("[1-4]")) {
                            valid = false;
                            break;
                        }
                    }

                    if (!valid) {
                        System.out.println("⚠️ Invalid input. Please enter only numbers between 1 and 4, separated by commas.");
                    } else {
                        break; // valid input
                    }
                }

                String correctAnswers = quizQuestions[i][5];
                boolean allCorrect = true;
                for (String ans : answers) {
                    if (!correctAnswers.contains(ans)) {
                        allCorrect = false;
                        break;
                    }
                }
                if (allCorrect && answers.length == correctAnswers.split(",").length) {
                    score++;
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect.");
                }
            }

            System.out.println("\nYour score: " + score + " out of " + quizQuestions.length);

            System.out.print("Would you like to try the quiz again? (y/n): ");
            retry = scanner.nextLine().trim();

        } while (retry.equalsIgnoreCase("y") || retry.equalsIgnoreCase("yes"));

        System.out.println("Returning to main menu.\n");

    } catch (InputCancelledException e) {
        System.out.println("❌ Quiz cancelled. Returning to main menu.\n");
    }
    
} 

  private static void trackEfforts() {
    System.out.println("\n✅ Track Your Conservation Efforts for the Past Week:");
    System.out.println("Type 'q' at any time to cancel and return to the main menu.");

    String[][] effortPrompts = {
        {"EngineOffIdle", "Turned off the engine instead of idling"},
        {"CombinedTrips", "Combined errands to reduce driving trips"},
        {"ThermostatSet", "Set thermostat to 78°F or higher"},
        {"FanUse", "Used fans to boost AC efficiency"}
    };

    int totalQuestions = 7 * effortPrompts.length; // total questions asked
    int score = 0;
    int answeredCount = 0; // count only yes/no answers

    try {
        for (int day = 1; day <= 7; day++) {
            System.out.println("\nDay " + day + ":");
            for (String[] effort : effortPrompts) {
                String answer = getYesNoInput("- " + effort[1] + " (yes/no/skip): ");
                switch (answer) {
                    case "yes":
                        score += 1;
                        answeredCount++;
                        break;
                    case "no":
                        score -= 1;
                        answeredCount++;
                        break;
                    case "skip":
                        // skip - no change and not counted as answered
                        break;
                }
            }
        }

        if (answeredCount == 0) {
            // No answers given (all skipped) - rating 1/5
            System.out.println("\nYou skipped all questions, so your energy-saving rating is 1/5.");
            System.out.println("🙁 Let's work on improving your habits.");
            return;
        }

        // Normalize score for answered questions only
        int maxScore = answeredCount; // max +1 for each yes
        int minScore = -answeredCount; // min -1 for each no

        // Shift score to be >= 0
        int normalizedScore = score - minScore; // add absolute min to shift range [minScore..maxScore] to [0..maxScore-minScore]

        int range = maxScore - minScore; // should be answeredCount * 2

        double ratio = (double) normalizedScore / range; // between 0 and 1

        int rating;
        if (ratio >= 0.85) rating = 5;
        else if (ratio >= 0.65) rating = 4;
        else if (ratio >= 0.45) rating = 3;
        else if (ratio >= 0.25) rating = 2;
        else rating = 1;

        System.out.println("\n🎉 Thank you for logging your efforts this week! Every step matters.\n");
        System.out.println("⭐ Your energy-saving rating for this week: " + rating + "/5");
        switch (rating) {
            case 5:
                System.out.println("🌟 Excellent! You're an energy-saving superstar!");
                break;
            case 4:
                System.out.println("👍 Great job! Keep up the good work!");
                break;
            case 3:
                System.out.println("🙂 You're doing okay, but there's room for improvement.");
                break;
            case 2:
                System.out.println("😕 Try a little harder next week.");
                break;
            case 1:
                System.out.println("🙁 Let's work on improving your habits.");
                break;
        }
        askReturnOrExit();

    } catch (InputCancelledException e) {
        System.out.println("❌ Input cancelled. Returning to main menu.\n");
    }
}

    private static void addEffort(String key, int count) {
        conservationEfforts.put(key, conservationEfforts.getOrDefault(key, 0) + count);
    }
    private static int getIntInput(String prompt) {
    while (true) {
        try {
            String input = getInput(prompt);
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid input. Please enter a number or 'q' to cancel.");
        }
    }
}
private static String getInput(String prompt) {
    System.out.print(prompt);
    String input = scanner.nextLine().trim();
    if (input.equalsIgnoreCase("q")) {
        throw new InputCancelledException();
    }
    return input;
}
    
    private static void showEffortsSummary() {
        System.out.println("\n📊Your Conservation Efforts Summary:");
        System.out.println("- Engine off instead of idling: " + conservationEfforts.getOrDefault("EngineOffIdle", 0));
        System.out.println("- Combined errands to reduce trips: " + conservationEfforts.getOrDefault("CombinedTrips", 0));
        System.out.println("- Thermostat set to 78°F or higher: " + conservationEfforts.getOrDefault("ThermostatSet", 0));
        System.out.println("- Used fans to boost AC efficiency: " + conservationEfforts.getOrDefault("FanUse", 0));
        System.out.println();
        askReturnOrExit();
    }

    private static void printLocalResources() {
        System.out.println("\n🌍Local Energy-Saving Resources:");
        System.out.println("- Check your utility company’s website for energy-saving programs and rebates.");
        System.out.println("- Explore local government initiatives for home energy audits.");
        System.out.println("- Visit the DOE ENERGY STAR website for tips and certified products.");
        System.out.println("- Look for community workshops or seminars on energy conservation.");
        System.out.println("- Use apps that monitor and help reduce your home energy consumption.");
        System.out.println();
        askReturnOrExit();
    }
    private static void runVirtualAssistant() {
        System.out.println("\n Hello! I'm your Energy Conservation Coach. Let's find ways to save energy together!");
        System.out.println("I'll ask you a few questions and offer some tips.");

        // Tip 1: Lighting
        System.out.println("\nTip 1: Lighting Efficiency.");
        System.out.println("Did you know replacing old incandescent bulbs with LED lights can save a lot of energy?");
        String response1 = getYesNoInput("Have you switched to LED lights in most of your home? (yes/no): ");
    if (response1.equals("yes")) {
        System.out.println("That's fantastic! You're already making a big difference. Keep it up!");
    } else {
        System.out.println("No worries! Consider trying LEDs for your most used lights. It's a simple change with a noticeable impact.");
    }

        // Tip 2: Unplugging Electronics
        System.out.println("\nTip 2: Eliminating Phantom Load.");
        System.out.println("Many electronics consume power even when turned off, often called 'phantom load'.");
        String response2 = getYesNoInput("Do you unplug chargers and electronics when not in use? (yes/no): ");
    if (response2.equals("yes")) {
        System.out.println("Excellent! Every unplugged device helps reduce wasted energy.");
    } else {
        System.out.println("It's easy to forget, but unplugging can save a surprising amount over time. Maybe try it with one device today?");
    }

        // Tip 3: Water Heating
        System.out.println("\nTip 3: Efficient Water Use.");
        System.out.println("Heating water uses a lot of energy. Taking shorter showers or using cold water for laundry can help.");
        String response3 = getYesNoInput("Do you consciously try to reduce your hot water usage, perhaps with shorter showers? (yes/no): ");
    if (response3.equals("yes")) {
        System.out.println("Awesome! Your efforts in conserving hot water are very impactful.");
    } else {
        System.out.println("Even a small reduction in hot water use can make a difference. It's worth a try!");
    }
        

        System.out.println("\nRemember, every small step contributes to a greener planet and lower bills. Keep up the great work!");
        System.out.println("I'm here to help whenever you need more tips!");
        
        askReturnOrExit();
    }
    private static String getYesNoInput(String prompt) {
    while (true) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("yes") || input.equals("no") || input.equals("skip")) {
            return input;
        } else if (input.equals("q")) {
            throw new InputCancelledException();
        } else {
            System.out.println("⚠️ Please answer 'yes', 'no', 'skip', or 'q' to cancel.");
        }
    }
}
    private static void askReturnOrExit() {
        while (true) {
            System.out.print("\nWould you like to return to the main menu or exit? (menu/exit): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("menu")) {
                return; // Go back to menu
            } else if (choice.equals("exit")) {
                printFarewell();
                System.exit(0);
            } else {
                System.out.println("⚠️ Invalid choice. Please type 'menu' or 'exit'.");
            }
        }
    }
    private static void printFarewell() {
        System.out.println("\nThank you for using the Energy Conservation App!");
        System.out.println("Every step counts. Share these tips and protect our planet.");
        System.out.println("Goodbye!");
    }
  private static void clearConsole() {
    try {
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        }
    } catch (Exception e) {
        // fallback to blank lines if exception occurs
        for (int i = 0; i < 50; i++) System.out.println();
    }
  }
}
