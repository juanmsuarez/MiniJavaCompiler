class A {
    private int i;
    public boolean b;

    A() {
        i = 10;
        b = true;

        System.printIln(fi());
        System.printBln(fA().fA().b);
        System.printBln(fb());
        f1(true);
        f1(false);
        f2(4);
        f2(2);
    }

    dynamic int fi() {
        return i * 3;
        System.printSln("unreachable");
        i = -1000;
        return i;
    }

    static A fA() {
        return new A();
        System.printSln("unreachable");
        return null;
    }

    dynamic boolean fb() {
        return !b;
        System.printSln("unreachable");
    }

    dynamic void f1(boolean x) {
        if (x) {
            System.printSln("true");
            return;
        }
        System.printSln("false");
        return;
        System.printSln("unreachable");
    }

    static void f2(int x) {
        while (x < 5) {
            System.printIln(x);
            if (x == 4) {
                return;
            }
            x = x + 1;
        }
        System.printSln("unreachable");
    }
}

class Main {
    static void main() {
        new A();
    }
}
