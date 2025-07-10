import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class map {
    public static class PunktXY {
        private double x;
        private double y;
        public PunktXY(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        @Override
        public String toString() {
            return "PunktXY{" + "x=" + x + ", y=" + y + '}';
        }
    }
    public static class PunktXYZ {
        private double x;
        private double y;
        private double z;

        public PunktXYZ(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        @Override
        public String toString() {
            return "PunktXYZ{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
        }
    }

    public static void main(String[] args) {
        List<PunktXYZ> punktyXYZ = new ArrayList<>();
        punktyXYZ.add(new PunktXYZ(1.0, 2.0, 3.0));
        punktyXYZ.add(new PunktXYZ(4.5, 5.5, 6.5));
        punktyXYZ.add(new PunktXYZ(7.0, 8.0, 9.0));
        punktyXYZ.add(new PunktXYZ(10.1, 11.1, 12.1));

        List<PunktXY> punktyXY = punktyXYZ.stream()
                .map(punktXYZ -> new PunktXY(punktXYZ.getX(), punktXYZ.getY()))
                .collect(Collectors.toList());

        System.out.println("Współrzędne punktów XY:");
        for (PunktXY punkt : punktyXY) {
            System.out.println("x: " + punkt.getX() + ", y: " + punkt.getY());
        }
    }
}