import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;
 
public class PolynomialRegression {
    private static void polyRegression(double[] x, double[] y) {
        int n = x.length;
        int[] r = IntStream.range(0, n).toArray();
        double xm = Arrays.stream(x).average().orElse(Double.NaN);
        double ym = Arrays.stream(y).average().orElse(Double.NaN);
        double x2m = Arrays.stream(r).map(a -> a * a).average().orElse(Double.NaN);
        double x3m = Arrays.stream(r).map(a -> a * a * a).average().orElse(Double.NaN);
        double x4m = Arrays.stream(r).map(a -> a * a * a * a).average().orElse(Double.NaN);
        double xym = 0.0;
        for (int i = 0; i < x.length && i < y.length; ++i) {
            xym += x[i] * y[i];
        }
        xym /= Math.min(x.length, y.length);
        double x2ym = 0.0;
        for (int i = 0; i < x.length && i < y.length; ++i) {
            x2ym += x[i] * x[i] * y[i];
        }
        x2ym /= Math.min(x.length, y.length);
 
        double sxx = x2m - xm * xm;
        double sxy = xym - xm * ym;
        double sxx2 = x3m - xm * x2m;
        double sx2x2 = x4m - x2m * x2m;
        double sx2y = x2ym - x2m * ym;
 
        double b = (sxy * sx2x2 - sx2y * sxx2) / (sxx * sx2x2 - sxx2 * sxx2);
        double c = (sx2y * sxx - sxy * sxx2) / (sxx * sx2x2 - sxx2 * sxx2);
        double a = ym - b * xm - c * x2m;
 
        IntToDoubleFunction abc = (int xx) -> a + b * xx + c * xx * xx;
 
        System.out.println("y = " + a + " + " + b + "x + " + c + "x^2");
        System.out.println(" Input  Approximation");
        double xc[] = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3,1.4,1.5};
		for (double ind:xc) {
			System.out.println(""+ind + "  "+(a+(b*ind)+ (c*ind*ind)));
		}
        System.out.println(" x   y     y1");
//        for (int i = 0; i < n; ++i) {
//            System.out.printf("%2d %3d  %5.1f\n", x[i], y[i], abc(x[i]));
//        }
    }
    public static double[] loadData(String filename) throws IOException {
		int length = 4177;
		//int length = 10;
		
		double x[] = new double[length];
		ArrayList<String> xcoord = new ArrayList<>();
		String foldername = "C:\\Users\\shashwat\\eclipse-workspace-new\\Test\\src\\";
		
		BufferedReader br = new BufferedReader(
				new FileReader(foldername+filename));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			xcoord.add(line);
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				xcoord.add(line);
			}
			xcoord.add(line);
		} finally {
			br.close();
		}
		System.out.println("xcoord length" + xcoord.size());
		for (int i = 0; i < length; i++) {
			x[i] = Double.parseDouble(xcoord.get(i));
		}
		return x;
	}
    
    public static void main(String[] args) throws IOException {
      
        double rings[] = loadData("rings.txt");
		double lengthweights[] = loadData("lengthweight.txt");
		double shellweights[] = loadData("shellweight.txt");
		double wholeweights[] = loadData("wholeweights.txt");
		double shuckedweights[] = loadData("shuckedweight.txt");
		double visceraweights[] = loadData("visceraweight.txt");
		double diameter[] = loadData("diameter.txt");
        
        polyRegression(lengthweights, rings);
        polyRegression(shellweights, rings);
        polyRegression(wholeweights, rings);
        polyRegression(shuckedweights, rings);
        polyRegression(visceraweights, rings);
        polyRegression(visceraweights, rings);
        
    }
}