package extras;

public class Histogram {

    public static int[] getBins(double[] data, int nBins, double start, double end) {

	int[] bins = new int[nBins];
	double delta = (end - start) / (nBins);
	int len = nBins;
	for (int i = 0; i < data.length; i++) {
	    int id = (int) ((data[i] - start) / delta);
	    if (id < 0) {
		id = 0;
	    }
	    if (id > len - 1) {
		id = len - 1;
	    }
	    bins[id]++;
	    /*
	     * System.out.println(data[i]+ " " + id);
	     */
	}
	return bins;
    }

    /*
     * public static void main(String[] args) { int[] bins; bins = getData(new
     * double[] {-1,1,2,3,4,5,6,6,7,8,2,3,5,2,45,7}, 3, 1,10);
     * 
     * for(int i = 0; i<bins.length; i++){ System.out.print(bins[i]+", "); }
     * 
     * }
     */

}
