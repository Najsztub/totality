package extras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PlotHist extends JPanel {
	private int[] bins;
	private int nBins;	
	private double maxy = 400;

	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		super.setBackground(Color.WHITE);
		if(nBins != 0) drawHist(g2);
	}

	public void addData(double[] dat, int nBins, double start, double end) {
		this.nBins = nBins;
		bins = Histogram.getBins(dat, nBins, start, end);

	}

	private void drawHist(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		int xSiz = this.getWidth();
		int ySiz = this.getHeight();

		double delta = xSiz / bins.length;

		for (int x = 0; x <= nBins-1; x++) {
			double yHProp = bins[x]/maxy;
			int yHeight = (int)((1-yHProp) * ySiz);
			g.fillRect((int)(delta * x), yHeight, (int)(delta-1), ySiz - yHeight);

		}
	}

}
