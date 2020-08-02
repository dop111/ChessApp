package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import Moves.Move;

/**
 * The table model for displaying the move history
 * @author Daniele Palazzo
 *
 */
public class MovesLogTableModel extends AbstractTableModel {

	private String[] colNames = {"White","Black"};
	
	private int rowCount;
	
	private List<Move> moves;
	
	/**
	 * Constructs and initialises a MovesLogTableModel
	 * @param moves A reference to the list of moves to be displayed in the table
	 */
	public MovesLogTableModel(List<Move> moves) {
		this.moves = moves;
		rowCount=moves.size()+2;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowCount() {
		return rowCount/2;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex*2 + columnIndex >= moves.size()) return "";
		return moves.get(rowIndex*2 + columnIndex).getLongAlgebraicNotation();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireTableDataChanged() {
		rowCount=moves.size()+2;
		super.fireTableDataChanged();
	}
}
