package hw3_4;

public class TestClass {
    private final Object monitor = new Object();
    private volatile static int currLetter = 0;
    private static char[] letters = {'A', 'B', 'C'};

    private void printLetter(char letter) {
        synchronized (monitor) { 
            try {
                for (int i = 0; i < 5; i++) {
                    while (letters[currLetter] != letter) {
                        monitor.wait();
                    }
                    System.out.print(letter);
                    currLetter = (currLetter + 1) % letters.length;
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TestClass testClass = new TestClass();

        Thread t1 = new Thread(() -> testClass.printLetter('A'));
        Thread t2 = new Thread(() -> testClass.printLetter('B'));
        Thread t3 = new Thread(() -> testClass.printLetter('C'));

        t1.start();
        t2.start();
        t3.start();
    }
}
