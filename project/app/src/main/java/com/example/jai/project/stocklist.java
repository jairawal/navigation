package com.example.jai.project;

/**
 * Created by Jai on 17-02-2018.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
public class stocklist extends  ArrayAdapter<String> {
    private Activity context;
    List<String> stocks;

    public stocklist(Activity context, List<String> stocks) {
        super(context, R.layout.layout_item_list, stocks);
        this.context = context;
        this.stocks = stocks;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_item_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        String stock = stocks.get(position);
        System.out.println(position +"   "+stocks.get(position).toString()+"  "+stock);
         textViewName.setText(stock);

        return listViewItem;
    }
}
