package simulation;

import javax.swing.JFrame;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import economy.HH;
import economy.Pers;
import economy.Population;
import extras.PlotHist;

public class SimGUI extends JFrame {

	private static final long serialVersionUID = 1789460948747856891L;
	private static Chart2D chart1;
	private static Chart2D chart2;
	private static Chart2D chart3;
	private static PlotHist hist;

	// Create an ITrace:
	private static ITrace2D trace1;
	private static ITrace2D trace2;
	private static ITrace2D trace3;
	public static Population popul;
	private static int pts;
	private static JPanel panel;
	private static JLabel lblNoHhs;
	private static JLabel lblHhs;
	private static JTextField fieldKill;
	private static JButton btnKill;
	private static JButton btnSimulate;
	private static JButton btnReset;
	private static JLabel lblGovBal;
	private static JTable table;

	private static ParamModel paramGlobals;

	public static double sumArr(int[] ar) {
		double sum = 0;
		for (int i = 0; i < ar.length; i++) {
			sum += ar[i];
		}
		return sum;
	}

	public static double sumArr(double[] ar) {
		double sum = 0;
		for (int i = 0; i < ar.length; i++) {
			sum += ar[i];
		}
		return sum;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SimGUI framew = new SimGUI();
					framew.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private SimGUI() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Population Simulation");
		setSize(1100, 600);

		chart1 = new Chart2D();
		chart2 = new Chart2D();
		chart3 = new Chart2D();

		// Create an ITrace:
		trace1 = new Trace2DLtd(300);
		trace2 = new Trace2DLtd(300);
		trace3 = new Trace2DLtd(300);

		chart1.addTrace(trace1);
		chart2.addTrace(trace2);
		chart3.addTrace(trace3);

		hist = new PlotHist();

		getContentPane().setLayout(new GridLayout(2, 3, 0, 0));

		getContentPane().add(chart1);
		getContentPane().add(chart2);

		getContentPane().add(chart3);
		getContentPane().add(hist);

		panel = new JPanel();
		getContentPane().add(panel);

		lblNoHhs = new JLabel("NO hhs: ");
		lblHhs = new JLabel("0");

		btnKill = new JButton("Kill");

		fieldKill = new JTextField();
		fieldKill.setText("0");
		fieldKill.setColumns(10);

		btnSimulate = new JButton("Simulate");

		btnReset = new JButton("Reset");

		JButton btnHhExplorer = new JButton("HH Explorer");
		btnHhExplorer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HHView hhview = new HHView();
				hhview.setPopul(popul);
				hhview.setVisible(true);
			}
		});

		JLabel lblGov = new JLabel("Gov: ");

		lblGovBal = new JLabel("0");

		popul = new Population();
		for (int i = 0; i < 1000; i++) {
			popul.addhh(new HH());
		}

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		lblNoHhs)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		lblHhs,
																		GroupLayout.PREFERRED_SIZE,
																		112,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		lblGov)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		lblGovBal))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		btnKill)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		fieldKill,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		btnSimulate)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		btnReset))
												.addComponent(btnHhExplorer))
								.addContainerGap(175, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblNoHhs)
												.addComponent(
														lblHhs,
														GroupLayout.PREFERRED_SIZE,
														15,
														GroupLayout.PREFERRED_SIZE))
								.addGap(3)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblGov)
												.addComponent(lblGovBal))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														fieldKill,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnKill))
								.addGap(9)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(btnSimulate)
												.addComponent(btnReset))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnHhExplorer)
								.addContainerGap(149, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		paramGlobals = new ParamModel();
		paramGlobals.pumpData();
		table = new JTable();
		table.setModel(paramGlobals);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);

		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int column = e.getColumn();
				TableModel model = (TableModel) e.getSource();
				String data = (String) model.getValueAt(row, column);
				if (column == 1) {
					popul.getParamsOut().setParamByID(Double.parseDouble(data),
							row);
				}

			}
		});

		// Add all points, as it is static:
		/*
		 * for (int i = 0; i <= 90; i++) { trace1.addPoint(i, popul.nPeople());
		 * trace2.addPoint(i, (float) sumArr(popul.getAges()) /
		 * popul.nPeople()); popul.step(); }
		 */
		// Make it visible:
		// Create a frame.

		pts = 0;
		btnKill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int age = Integer.parseInt(fieldKill.getText());
				popul.killAge(age);
				simulate();
			}
		});
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trace1.removeAllPoints();
				trace2.removeAllPoints();
				trace3.removeAllPoints();
				pts = 0;
				popul = new Population();
				Pers.nPers = 0;
				for (int i = 0; i < 1000; i++) {
					popul.addhh(new HH());
				}
			}
		});
		btnSimulate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulate();
			}
		});

	}
 
	private static void simulate() {
		trace1.addPoint(pts, popul.nPeople());
		double[] ages = popul.getAges();
		trace2.addPoint(pts, (float) sumArr(ages) / popul.nPeople());
		trace3.addPoint(pts, (float) popul.nPeople() / popul.size());
		pts++;
		lblHhs.setText(Integer.toString(popul.size()));
		lblGovBal.setText(Double.toString(Population.getGov().getBalance()));
		hist.addData(ages, 90, 0, 90);
		hist.revalidate();
		hist.repaint();
		popul.step();
	}

	class ParamModel extends AbstractTableModel {

		private static final long serialVersionUID = 4691448976955633559L;

		private String[] columnNames = new String[] { "Variable name", "Value" };

		private Object[][] data = {};

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		@Override
		public void setValueAt(Object obj, int row, int col) {
			data[row][col] = obj;
			fireTableCellUpdated(row, col);
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col > 0) {
				return true;
			} else {
				return false;
			}
		}

		public void pumpData() {
			int nPar = popul.getParamsOut().parLength();
			data = new Object[nPar][2];
			for (int i = 0; i < nPar; i++) {
				data[i][0] = popul.getParamsOut().getDescByID(i);
				data[i][1] = popul.getParamsOut().getParamByID(i);
			}
			fireTableDataChanged();
		}
	}
}
