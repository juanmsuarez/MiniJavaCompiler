class Main {
    static int fib(int x) {
        if (x == 0 || x == 1) {
            return 1;
        }
        return fib(x - 1) + fib(x - 2);
    }

    static void main() {
        while (true) {
            int x; x = System.read() - 48;
            System.read();
            System.printIln(fib(x));
        }
    }
}
