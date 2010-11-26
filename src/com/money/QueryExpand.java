package com.money;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

	public class QueryExpand extends BaseExpandableListAdapter 
	{

    	Activity ac;
    	
    	ArrayList<String> group;
    	ArrayList<List<String>> child;

    	public QueryExpand(Activity a)
    	{
    		//to initialize 2 contains
    		group = new ArrayList<String>();
    		child = new ArrayList<List<String>>();

    		ac = a;
    	}

        public void addItem(String p, String[] c)
        {

        	group.add(p);

        	List<String> item = new ArrayList<String>();

        	for(int i=0;i<c.length;i++){

        		item.add(c[i]);

        	}

        	child.add(item);

        }    	
    	
    	//child method stub
    	
		//@Override

		public Object getChild(int groupPosition, int childPosition) {

			// TODO Auto-generated method stub

			return child.get(groupPosition).get(childPosition);

		}

		//@Override

		public long getChildId(int groupPosition, int childPosition) {

			// TODO Auto-generated method stub

			return childPosition;

		}

		//@Override

		public int getChildrenCount(int groupPosition) {

			// TODO Auto-generated method stub

			return child.get(groupPosition).size();

		}

		//@Override

		public View getChildView(int groupPosition, int childPosition,

				boolean isLastChild, View convertView, ViewGroup parent) {

			// TODO Auto-generated method stub

			String string = child.get(groupPosition).get(childPosition);

			if(childPosition == 1){

				return getViewText(groupPosition,childPosition,string);

			}

			else {

				return getViewText(groupPosition,childPosition,string);

			}

		}

		//TextView Template

		private View  getViewText(int group,int child,String string){

			TextView tv = new TextView(ac);

			//tv.setText(string);

			if(child == 0){

				tv.setText(string);

			}

			else if(child == 1) {

				tv.setText(string);

			}

			else if(child == 2){

				tv.setText(string);

			}

			else {

				tv.setText(string);

			}

			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(

                    ViewGroup.LayoutParams.FILL_PARENT, 64);

			tv.setLayoutParams(lp);

			// Center the text vertically

            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            // Set the text starting position

            tv.setPadding(36, 0, 0, 0);

			return tv;

		}

		//group method stub

		//@Override

		public Object getGroup(int groupPosition) {

			// TODO Auto-generated method stub

			return group.get(groupPosition);

		}

		//@Override

		public int getGroupCount() {

			// TODO Auto-generated method stub

			return group.size();

		}

		//@Override

		public long getGroupId(int groupPosition) {

			// TODO Auto-generated method stub

			return groupPosition;

		}

		//@Override

		public View getGroupView(int groupPosition, boolean isExpanded,

				View convertView, ViewGroup parent) {

			// TODO Auto-generated method stub

			String string = group.get(groupPosition);

			return getGenericView(string);

		}

		//View stub to create Group/Children 's View

		public TextView getGenericView(String s) {

            // Layout parameters for the ExpandableListView

            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(

                    ViewGroup.LayoutParams.FILL_PARENT, 64);

            TextView text = new TextView(ac);

            text.setLayoutParams(lp);

            // Center the text vertically

            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            // Set the text starting position

            text.setPadding(36, 0, 0, 0);

            text.setText(s);

            return text;

        }

		//@Override

		public boolean hasStableIds() {

			// TODO Auto-generated method stub

			return false;

		}

		//@Override

		public boolean isChildSelectable(int groupPosition, int childPosition) {

			// TODO Auto-generated method stub

			return true;

		}

    }	