import java.io.*;
import java.util.ArrayList;

import static java.lang.Math.pow;

public class LinearRegression {
    private static double intercept =0;
	private static double slope= 0;
    private static double r2 =0 ;
    private static double svar0 =0;
	private static double svar1= 0;
    /**
     * Returns the <em>y</em>-intercept &alpha; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
     *
     * @return the <em>y</em>-intercept &alpha; of the best-fit line <em>y = &alpha; + &beta; x</em>
     */
    public double intercept() {
        return intercept;
    }

   /**
     * Returns the slope &beta; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
     *
     * @return the slope &beta; of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>
     */
    public double slope() {
        return slope;
    }

   /**
     * Returns the coefficient of determination <em>R</em><sup>2</sup>.
     *
     * @return the coefficient of determination <em>R</em><sup>2</sup>,
     *         which is a real number between 0 and 1
     */
    public double R2() {
        return r2;
    }

   /**
     * Returns the standard error of the estimate for the intercept.
     *
     * @return the standard error of the estimate for the intercept
     */
    public double interceptStdErr() {
        return Math.sqrt(svar0);
    }

   /**
     * Returns the standard error of the estimate for the slope.
     *
     * @return the standard error of the estimate for the slope
     */
    public double slopeStdErr() {
        return Math.sqrt(svar1);
    }

   /**
     * Returns the expected response {@code y} given the value of the predictor
     * variable {@code x}.
     *
     * @param  x the value of the predictor variable
     * @return the expected response {@code y} given the value of the predictor
     *         variable {@code x}
     */
    public double predict(double x) {
        return slope*x + intercept;
    }

   /**
     * Returns a string representation of the simple linear regression model.
     *
     * @return a string representation of the simple linear regression model,
     *         including the best-fit line and the coefficient of determination
     *         <em>R</em><sup>2</sup>
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%.2f n + %.2f", slope(), intercept()));
        s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
        return s.toString();
    }
    
    
	// function to calculate m and c that best fit points
	// represented by x[] and y[]
	static void bestApproximate(double x[], double y[]) {
		int n = x.length;
		double m, c, sum_x = 0, sum_y = 0, sum_xy = 0, sum_x2 = 0;
		for (int i = 0; i < n; i++) {
			sum_x += x[i];
			sum_y += y[i];
			sum_xy += x[i] * y[i];
			sum_x2 += pow(x[i], 2);
		}
		
		m = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - pow(sum_x, 2));
		c = (sum_y - m * sum_x) / n;

		System.out.println("slope = " + m);
		System.out.println("intercept = " + c);
		
		double xc[] = {0.1,0.2,0.3,0.4,0.5,0.6,0.7};
		for (double b:xc) {
			System.out.println(""+b + "  "+((m*b)+c));
		}
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
	
	
	// Driver main function
	public static void main(String args[]) throws IOException {
		double rings[] = loadData("rings.txt");
		double lengthweights[] = loadData("lengthweight.txt");
		double shellweights[] = loadData("shellweight.txt");
		double wholeweights[] = loadData("wholeweights.txt");
		double shuckedweights[] = loadData("shuckedweight.txt");
		double visceraweights[] = loadData("visceraweight.txt");
		double diameter[] = loadData("diameter.txt");
		
		 bestApproximate(rings, lengthweights); 
		 System.out.println();
		 bestApproximate(rings, shellweights);
		 System.out.println();
		 bestApproximate(rings, wholeweights);
		 System.out.println();
		 bestApproximate(rings, shuckedweights);
		 System.out.println();
		 bestApproximate(rings, visceraweights);
		 System.out.println();
		 bestApproximate(rings, diameter);
		 		 
	}
	
	
	public static  void  methodtoTry(double x[], double y[]) {
		if (x.length != y.length) {
            throw new IllegalArgumentException("array lengths are not equal");
        }
        int n = x.length;

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
        for (int i = 0; i < n; i++) {
            sumx  += x[i];
            sumx2 += x[i]*x[i];
            sumy  += y[i];
        }
        double xbar = sumx / n;
        double ybar = sumy / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        slope  = xybar / xxbar;
        System.out.println("slope"+slope);
        intercept = ybar - slope * xbar;
        System.out.println("intercept"+intercept);

        // more statistical analysis
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = slope*x[i] + intercept;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }

        int degreesOfFreedom = n-2;
        r2    = ssr / yybar;
        double svar  = rss / degreesOfFreedom;
        svar1 = svar / xxbar;
        svar0 = svar/n + xbar*xbar*svar1;
	}
	
}
