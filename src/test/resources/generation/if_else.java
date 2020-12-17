class A {
    static void simpleSentences() {
        System.printSln("check_cond");
        if (true) System.printSln("main body");
        System.printSln("end_if");

        System.printSln("check_cond");
        if (false) System.printSln("main body");
        System.printSln("end_if");

        System.printSln("check_cond");
        if (true) System.printSln("main body");
        else System.printSln("else body");
        System.printSln("end_if");

        System.printSln("check_cond");
        if (false) System.printSln("main body");
        else System.printSln("else body");
        System.printSln("end_if");
    }

    static void blocks() {
        int x; x = 3;
        boolean cond1 = x < 5;
        System.printSln("check_cond");
        if (cond1) {
            System.printSln("main body 1");
            System.printSln("main body 2");
        }
        System.printSln("end_if");

        int y; y = 3 * 5;
        boolean cond2; cond2 = y < 5;
        System.printSln("check_cond");
        if (cond2) {
            System.printSln("main body 1");
            System.printSln("main body 2");
        }
        else {
            System.printSln("else body 1");
            System.printSln("else body 2");
        }
        System.printSln("end_if");
    }
}

class Main {
    static void main() {
        A.simpleSentences();
        A.blocks();
    }
}
