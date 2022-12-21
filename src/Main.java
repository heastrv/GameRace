import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        int t = scanner.nextInt();
        int t1 = scanner.nextInt();

        System.out.println(t*t/t1+" "+ (t*t%t1)*60/t1);
    }
}