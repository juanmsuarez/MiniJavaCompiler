class A {
    static void f() {
        int x; x = 0;
        System.printSln("in");
        while (x < 5) {
            System.printIln(x);
            int y; y = 0;
            while (y < 5) {
                y = y + 1;
            }
            System.printIln(y);
            x = x + 1;
        }
        System.printSln("out");
        System.printIln(x);
    }
}

class Main {
    static void main() {
        System.printSln("in");
        while (false) {
            System.printSln("1");
        }
        System.printSln("out");

        System.printSln("in");
        boolean cond; cond = true;
        while (cond) {
            System.printSln("2");
            new A().f();
            cond = false;
        }
        System.printSln("out");
    }
}
