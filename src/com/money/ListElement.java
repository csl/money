package com.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public interface ListElement {
	public int getLayoutId();

	public boolean isClickable();

	public View getViewForListElement(LayoutInflater layoutInflater,
			Context context, View view);
}
