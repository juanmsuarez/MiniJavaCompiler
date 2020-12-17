class Main {
    static void main() {
        System.printBln(!false);
        System.printIln(+1);
        System.printIln(-1);

        System.printIln(3 - 1 + 2);
        System.printIln(2 * 3 - 4 / 2);
        System.printIln(7 % 2);

        System.printBln(null == null);
        System.printBln(new Main() != null);
        System.printBln(2 * 3 == 12 / 2);
        System.printBln(3 - 1 != 4);

        System.printBln(1 < 2);
        System.printBln(3 <= 3);
        System.printBln(3 > 4);
        System.printBln(4 >= 4);

        System.printBln(1 < 2 && null == null);
        System.printBln(3 > 4 && null == null);
        System.printBln(3 > 4 || null == null);
    }
}
