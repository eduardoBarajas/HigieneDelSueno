package com.barajasoft.higienedelsueo.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barajasoft.higienedelsueo.Models.CellModel;
import com.barajasoft.higienedelsueo.Models.ColumHeaderModel;

import com.barajasoft.higienedelsueo.Models.RowHeaderModel;
import com.barajasoft.higienedelsueo.R;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

public class RegistrosTableViewAdapter extends AbstractTableAdapter<ColumHeaderModel, RowHeaderModel, CellModel> {

    public RegistrosTableViewAdapter(Context context){
        super(context);
    }

    class mCellViewHolder extends AbstractViewHolder{

        public final TextView cell_textview;

        public mCellViewHolder(View itemView){
            super(itemView);
            cell_textview = itemView.findViewById(R.id.cell_data);
        }

    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType){
        // Get cell xml layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout.tableview_cell_model,parent,false);
        //create a custom viewHolder for a cell item
        return new mCellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
        CellModel cell = (CellModel) cellItemModel;

        mCellViewHolder viewHolder = (mCellViewHolder)holder;
        viewHolder.cell_textview.setText(cell.getData().toString());

//        viewHolder.itemView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        viewHolder.cell_textview.requestLayout();
    }

    class mColumnHeaderViewHolder extends AbstractViewHolder {

        public final TextView column_header_textView;

        public mColumnHeaderViewHolder(View itemView) {
            super(itemView);
            column_header_textView = (TextView) itemView.findViewById(R.id.column_header_textView);
        }
    }

    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        return new mColumnHeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.tableview_column_header_model,parent,false));
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnheaderItemModel, int position){
        ColumHeaderModel columnHeader = (ColumHeaderModel)columnheaderItemModel;

        //get the holder to update cell item text
        mColumnHeaderViewHolder columnHeaderViewHolder = (mColumnHeaderViewHolder)holder;
        columnHeaderViewHolder.column_header_textView.setText(columnHeader.getData());

        //it is necessary to reameasure itself
//        columnHeaderViewHolder.column_header_textView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        columnHeaderViewHolder.column_header_textView.requestLayout();
    }

    class mRowHeaderViewHolder extends AbstractViewHolder{

        public final TextView row_header_textview;

        public mRowHeaderViewHolder(View itemView){
            super(itemView);
            row_header_textview = itemView.findViewById(R.id.row_header_textview);
        }
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType){

        //Get Row Header xml Layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout.tableview_rowheader_model,parent,false);

        return new mRowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int position){
        RowHeaderModel rowHeader = (RowHeaderModel) rowHeaderItemModel;
//
//        mRowHeaderViewHolder rowHeaderViewHolder = (mRowHeaderViewHolder) holder;
//        rowHeaderViewHolder.row_header_textview.setText(rowHeader.getData());
    }

    @Override
    public View onCreateCornerView(){
        return LayoutInflater.from(mContext).inflate(R.layout.tableview_cell_model,null);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int rowPosition) {
        // The unique ID for this type of row header item
        // If you have different items for Row Header View by Y (Row) position,
        // then you should fill this method to be able create different
        // type of RowHeaderViewHolder on "onCreateRowHeaderViewHolder"
        return 0;
    }

    @Override
    public int getCellItemViewType(int columnPosition) {
        // The unique ID for this type of cell item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        return 0;
    }
}
