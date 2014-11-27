package extras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PlotHist extends JPanel {
	int[] bins;
	int nBins;
	double start;
	double end;

	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// g2.setFont(font);
		super.paintComponent(g2);
		super.setBackground(Color.WHITE);
		// drawString(g2, outString, 3, 10);
		if(nBins != 0) drawHist(g2);
	}

	public void addData(double[] dat, int nBins, double start, double end) {
		this.nBins = nBins;
		this.start = start;
		this.end = end;

		bins = Histogram.getBins(dat, nBins, start, end);

	}

	private void drawHist(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		int xSiz = this.getWidth();
		int ySiz = this.getHeight();

		double delta = xSiz / bins.length;
		double maxy = 400;

		for (int x = 0; x <= nBins-1; x++) {
			double yHProp = bins[x]/maxy;
			int yHeight = (int)((1-yHProp) * ySiz);
			g.fillRect((int)(delta * x), yHeight, (int)(delta-1), ySiz - yHeight);

		}
	}

}
