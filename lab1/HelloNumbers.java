public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int o = 0;
        while (x < 10) {
            System.out.print(x + o + " ");
            o = x + o;
            x = x + 1;
        }
    }
}