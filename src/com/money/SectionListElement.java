package com.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SectionListElement implements ListElement 
{

	private String text;

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int getLayoutId() {
		return R.layout.section;
	}

	@Override
	public boolean isClickable() {
		return false;
	}

	@Override
	public View getViewForListElement(LayoutInflater layoutInflater,
			Context context, View view) {
		LinearLayout layout = (LinearLayout) layoutInflater.inflate(getLayoutId(), null);
		TextView textView=(TextView) layout.findViewById(R.id.section_title);
		textView.setText(text);
		return layout;
	}

}
