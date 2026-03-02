public class Main {
    public static void main(String[] args) {
        System.out.println("=== Demo Immutable Objects ===");

        java.util.List<Integer> oneD = new java.util.ArrayList<>();
        oneD.add(10);
        oneD.add(20);

        java.util.List<java.util.List<Integer>> twoD = new java.util.ArrayList<>();
        java.util.List<Integer> row = new java.util.ArrayList<>();
        row.add(1);
        row.add(2);
        twoD.add(row);

        DemoImmutable original = DemoImmutable.builder()
                .a(100)
                .b("immutable")
                .c(5)
                .oneDList(oneD)
                .twoDList(twoD)
                .build();

        oneD.add(999);
        row.add(999);

        System.out.println("Original oneD list after external change attempt: " + original.getOneDList());
        System.out.println("Original twoD list after external change attempt: " + original.getTwoDList());

        DemoImmutable updated = original.withC(99);
        System.out.println("Original c=" + original.getC() + ", updated c=" + updated.getC());

    }
}
