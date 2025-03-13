public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        String classpath = System.getProperty("java.class.path");
        System.out.println("Classpath: " + classpath);
        // Solution p2 = new Solution();
        
        System.out.println("Result: " + Solution.compute());

    }
}

