public class Main {
    public static void main(String[] args) {
        FloatingPoint f = new FloatingPoint(1.2);
        FloatingPoint fp = new FloatingPoint(2.4);
        System.out.println(f.getPrecision());
        f.setPrecision(2.0);
        System.out.println(f.getPrecision());
        f.add(fp);
        System.out.println(f.getPrecision());
    }
}
