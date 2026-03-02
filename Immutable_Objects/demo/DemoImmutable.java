import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable class demo.
 *
 * Why each rule matters:
 * 1) final class: prevents subclass from adding mutability.
 * 2) private final fields: assigned once, not re-assigned later.
 * 3) no setters / no mutating methods: state never changes after construction.
 * 4) defensive copy on input: caller cannot hold internal references.
 * 5) defensive copy on output: caller cannot mutate internal collections.
 *
 * Builder is used to avoid telescoping constructors and to make optional fields
 * explicit.
 */
public final class DemoImmutable {
    private final int a;
    private final String b;
    private final int c;
    private final List<Integer> oneDList;
    private final List<List<Integer>> twoDList;

    private DemoImmutable(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;

        List<Integer> copy1d = new ArrayList<>();
        copy1d.addAll(builder.oneDList);
        this.oneDList = Collections.unmodifiableList(copy1d);

        List<List<Integer>> deepCopy2d = new ArrayList<>();
        for (List<Integer> inner : builder.twoDList) {
            List<Integer> innerCopy = new ArrayList<>();
            innerCopy.addAll(inner);
            deepCopy2d.add(Collections.unmodifiableList(innerCopy));
        }
        this.twoDList = Collections.unmodifiableList(deepCopy2d);
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public List<Integer> getOneDList() {
        return new ArrayList<>(oneDList);
    }

    public List<List<Integer>> getTwoDList() {
        List<List<Integer>> deepCopy = new ArrayList<>();
        for (List<Integer> inner : twoDList) {
            deepCopy.add(new ArrayList<>(inner));
        }
        return deepCopy;
    }

    public DemoImmutable withC(int newC) {
        return DemoImmutable.builder()
                .a(this.a)
                .b(this.b)
                .c(newC)
                .oneDList(this.oneDList)
                .twoDList(this.twoDList)
                .build();
    }

    public static final class Builder {
        private int a;
        private String b = "";
        private int c;
        private List<Integer> oneDList = new ArrayList<>();
        private List<List<Integer>> twoDList = new ArrayList<>();

        public Builder a(int a) {
            this.a = a;
            return this;
        }

        public Builder b(String b) {
            this.b = (b == null) ? "" : b;
            return this;
        }

        public Builder c(int c) {
            this.c = c;
            return this;
        }

        public Builder oneDList(List<Integer> values) {
            this.oneDList = (values == null) ? new ArrayList<>() : new ArrayList<>(values);
            return this;
        }

        public Builder twoDList(List<List<Integer>> values) {
            this.twoDList = new ArrayList<>();
            if (values != null) {
                for (List<Integer> inner : values) {
                    this.twoDList.add(new ArrayList<>(inner));
                }
            }
            return this;
        }

        public DemoImmutable build() {
            return new DemoImmutable(this);
        }
    }
}
