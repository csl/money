package com.money;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CustomerListAdapter extends BaseAdapter 
{

	private Context context;

	protected ArrayList<ListElement> resultList;

	private LayoutInflater layoutInflater;
	
	public CustomerListAdapter(Context context) 
	{
		super();
		this.context = context;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService("layout_inflater");
		this.resultList = new ArrayList<ListElement>();
	}

	@Override
	public int getCount() {
		return this.resultList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.resultList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		return this.resultList.get(position).getViewForListElement(
				layoutInflater, context, view);
	}

	public void addList(List<ListElement> elements) {
		this.resultList.addAll(elements);
	}

	@Override
	public boolean isEnabled(int position) {
		return this.resultList.get(position).isClickable();
	}

	public void addSectionHeaderItem(String text) {
		SectionListElement element = new SectionListElement();
		element.setText(text);
		this.resultList.add(element);
	}
}
