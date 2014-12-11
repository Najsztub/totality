package simulation;

import economy.HH;
import economy.Population;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;

public class HHView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2692053439668888664L;
	private JPanel contentPane;
	private JTable table;
	private static Population popul;
	private static AllHHsModel tabMod;
	private static HHDesc hhDesc;
	private JTextArea textArea;
	private JTable table_1;
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					HHView frame = new HHView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HHView() {
		setTitle("Househol Explorer");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		table = new JTable();
		tabMod = new AllHHsModel();
		table.setModel(tabMod);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		scrollPane.setViewportView(table);

		scrollPane.setViewportBorder(UIManager.getBorder("CheckBox.border"));

		textArea = new JTextArea();
		hhDesc = new HHDesc();

		scrollPane_1 = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
								154, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_contentPane
										.createParallelGroup(Alignment.LEADING)
										.addComponent(textArea,
												GroupLayout.DEFAULT_SIZE, 276,
												Short.MAX_VALUE)
										.addComponent(scrollPane_1,
												GroupLayout.DEFAULT_SIZE, 276,
												Short.MAX_VALUE))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_contentPane
						.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								270, Short.MAX_VALUE)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addComponent(textArea,
												GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addGap(13)
										.addComponent(scrollPane_1,
												GroupLayout.DEFAULT_SIZE, 222,
												Short.MAX_VALUE))));
		table_1 = new JTable();
		table_1.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(hhDesc);
		contentPane.setLayout(gl_contentPane);

		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent event) {
						// do some actions here, for example
						// print first column value from selected row
						int id = (int) table.getValueAt(table.getSelectedRow(),
								0);
						getDesc(popul.households.get(id));
						hhDesc.pumpData(popul.households.get(id));

					}
				});

	}

	public void setPopul(Population pop) {
		popul = pop;
		Integer[] sizes = new Integer[popul.size()];
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = popul.households.get(i).members.size();
		}
		tabMod.pumpData(sizes);
	}

	private void getDesc(HH house) {
		textArea.setText("Disposible wealth in HH: " + house.getIncome());

	}

	class AllHHsModel extends AbstractTableModel {

		private static final long serialVersionUID = -4402898196808141211L;

		private String[] columnNames = new String[] { "HH id", "Size" };
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


		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		@Override
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

		public void pumpData(Object[] size) {
			data = new Object[size.length][2];
			for (int i = 0; i < size.length; i++) {
				data[i][0] = i;
				data[i][1] = size[i];
			}
			fireTableDataChanged();
		}
	}

	class HHDesc extends AbstractTableModel {
		private static final long serialVersionUID = -7104836441785438497L;

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


		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		@Override
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

		private String[] columnNames = new String[] { "id", "Sex", "Age",
				"Partner", "pLife", "Income", "Cons", "Utility" };
		private Object[][] data = {};

		public void pumpData(HH house) {
			int nHh = house.members.size();
			data = new Object[nHh][8];
			for (int i = 0; i < nHh; i++) {
				data[i][0] = i;
				data[i][1] = house.members.get(i).sex ? "M" : "F";
				data[i][2] = house.members.get(i).age;
				data[i][3] = house.members.get(i).partner != null ? "C" : "S";
				data[i][4] = house.members.get(i).pLife;
				data[i][5] = house.members.get(i).income;
				data[i][6] = house.members.get(i).consumption;
				data[i][7] = house.members.get(i).totalUtility;
			}
			fireTableDataChanged();
		}
	}
}
