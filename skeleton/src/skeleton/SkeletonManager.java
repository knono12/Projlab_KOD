package skeleton;
import java.util.Scanner;

public class SkeletonManager {

    private static int depth = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void call(String msg) {
        for (int i = 0; i < depth; i++) System.out.print("  ");
        System.out.println("-> " + msg);
        depth++;
    }

    public static void ret(String msg) {
        depth--;
        for (int i = 0; i < depth; i++) System.out.print("  ");
        System.out.println("<- " + msg);
    }

    public static boolean ask(String question) {
        for (int i = 0; i < depth; i++) System.out.print("  ");
        System.out.print("? " + question + " (I/N): ");
        String answer = scanner.nextLine().trim().toUpperCase();
        return answer.equals("I");
    }

    void skeletonLogic(int testCase){
        

    //TODO: implment

        switch (testCase) {
            case 1:
                break;
        
            default:
                break;
        }
    }
}
