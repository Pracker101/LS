import StringApp.StringOperationsPOA;

public class StringOperationsImpl extends StringOperationsPOA {
    public String concat(String a, String b) {
        return a + b;
    }

    public int length(String a) {
        return a.length();
    }

    public String reverse(String a) {
        return new StringBuilder(a).reverse().toString();
    }
}
