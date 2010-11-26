package com.money;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.util.Log;

public class MoneyListView extends ListActivity 
{
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String m_list  = (String) this.getResources().getText(R.string.m_list);
		String m_account  = (String) this.getResources().getText(R.string.m_account);
		String m_inoutcome  = (String) this.getResources().getText(R.string.m_inoutcome);
		
		String c_list  = (String) this.getResources().getText(R.string.c_list);
		String c_account  = (String) this.getResources().getText(R.string.c_account);
		String c_inoutcome  = (String) this.getResources().getText(R.string.c_inoutcome);
		
		String t_list  = (String) this.getResources().getText(R.string.t_list);
		String t_gunno  = (String) this.getResources().getText(R.string.t_gunno);
		
		String s_list  = (String) this.getResources().getText(R.string.s_list);
		String s_setup  = (String) this.getResources().getText(R.string.s_setup);
		
		CustomerListAdapter adapter = new CustomerListAdapter(this);
		ContentListElement element;

		//Management
		adapter.addSectionHeaderItem(m_list);

		ArrayList<ListElement> elements = new ArrayList<ListElement>();
	
		element = new ContentListElement();
		element.setTitle(m_account);
		elements.add(element);
		
		element = new ContentListElement();
		element.setTitle(m_inoutcome);
		elements.add(element);
		
		adapter.addList(elements);

		//Control
		adapter.addSectionHeaderItem(c_list);

		elements = new ArrayList<ListElement>();
/*		
		element = new ContentListElement();
		element.setTitle(c_account);
		elements.add(element);
	*/	
		element = new ContentListElement();
		element.setTitle(c_inoutcome);
		elements.add(element);

		adapter.addList(elements);

		//tools
		adapter.addSectionHeaderItem(t_list);

		elements = new ArrayList<ListElement>();

		element = new ContentListElement();
		element.setTitle(t_gunno);
		elements.add(element);

		adapter.addList(elements);

		//system setup
		adapter.addSectionHeaderItem(s_list);

		elements = new ArrayList<ListElement>();

		element = new ContentListElement();
		element.setTitle(s_setup);
		elements.add(element);
		adapter.addList(elements);

		this.setListAdapter(adapter);

	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		Log.d("DEBUG", "press " + position);

		if (position == 1)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,addaccount.class);
	
			startActivity(intent);
			MoneyListView.this.finish();
		}
		else if (position==2)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,additem.class);
	
			startActivity(intent);
			MoneyListView.this.finish();
		}
		else if (position == 4)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,query.class);
	
			startActivity(intent);
			MoneyListView.this.finish();
		}
		else if (position == 6)
		{
			Intent intent = new Intent();
			intent.setClass(MoneyListView.this,invoice.class);
	
			startActivity(intent);
			MoneyListView.this.finish();
		}
	}
}