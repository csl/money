package com.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContentListElement implements ListElement {

	private String title;
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int getLayoutId() 
	{
		return R.layout.item;
	}

	@Override
	public View getViewForListElement(LayoutInflater layoutInflater,
			Context context, View view) 
	{
		LinearLayout layout = (LinearLayout) layoutInflater.inflate(
				getLayoutId(), null);
		TextView textView = (TextView) layout.findViewById(R.id.title);
		textView.setText(title);
		return layout;
	}

	@Override
	public boolean isClickable() 
	{
		return true;
	}

}
