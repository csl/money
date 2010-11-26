package com.money;

import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnKeyListener;
import android.util.DisplayMetrics;

public class calc extends Activity 
{
    //GUI
    private EditText txtCalc;
    private Button btnZero;
	private Button btnOne;
    private Button btnTwo;
    private Button btnThree;
    private Button btnFour;
    private Button btnFive;
    private Button btnSix;
    private Button btnSeven;
    private Button btnEight;
    private Button btnNine;
    private Button btnPlus;
    private Button btnMinus;
    private Button btnMultiply;
    private Button btnDivide;
    private Button btnEquals;
    private Button btnC;
	private Button btnDecimal;
    private Button btnMC;
    private Button btnMR;
    private Button btnMM;
    private Button btnMP;
    private Button btnBS;
    private Button btnPerc;
    private Button btnSqrRoot;
    private Button btnPM;
    private Button btnOff;
    
   
    
	private double num = 0;
	private double memNum = 0;
    private int operator = 0; // 0 = nothing, 1 = plus, 2 = minus, 3 = multiply, 4 = divide
    private boolean readyToClear = false;
    private boolean hasChanged = false;
    
    private static final int MENU_START = Menu.FIRST  ;
	private static final int MENU_EXIT = Menu.FIRST +1 ;

    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc);
        
        //GUI
        txtCalc = (EditText)findViewById(R.id.txtCalc);
        btnZero = (Button)findViewById(R.id.btnZero);
        btnOne = (Button)findViewById(R.id.btnOne);
        btnTwo = (Button)findViewById(R.id.btnTwo);
        btnThree = (Button)findViewById(R.id.btnThree);
        btnFour = (Button)findViewById(R.id.btnFour);
        btnFive = (Button)findViewById(R.id.btnFive);
        btnSix = (Button)findViewById(R.id.btnSix);
        btnSeven = (Button)findViewById(R.id.btnSeven);
        btnEight = (Button)findViewById(R.id.btnEight);
        btnNine = (Button)findViewById(R.id.btnNine);
        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnMinus = (Button)findViewById(R.id.btnMinus);
        btnMultiply = (Button)findViewById(R.id.btnMultiply);
        btnDivide = (Button)findViewById(R.id.btnDivide);
        btnEquals = (Button)findViewById(R.id.btnEquals);
        btnC = (Button)findViewById(R.id.btnC);
        btnDecimal = (Button)findViewById(R.id.btnDecimal);
        btnMC = (Button)findViewById(R.id.btnMC);
        btnMR = (Button)findViewById(R.id.btnMR);
        btnMM = (Button)findViewById(R.id.btnMM);
        btnMP = (Button)findViewById(R.id.btnMP);
        btnBS = (Button)findViewById(R.id.btnBS);
        btnPerc = (Button)findViewById(R.id.btnPerc);
        btnSqrRoot = (Button)findViewById(R.id.btnSqrRoot);
        btnPM = (Button)findViewById(R.id.btnPM);
        
        btnOff = (Button)findViewById(R.id.off);  
        //method
        btnZero.setOnClickListener(new Button.OnClickListener() 
        { public void onClick (View v){ handleNumber(0); }});
        btnOne.setOnClickListener(new Button.OnClickListener() 
        { public void onClick (View v){ handleNumber(1); }});
        btnTwo.setOnClickListener(new Button.OnClickListener() 
        { public void onClick (View v){ handleNumber(2); }});
        btnThree.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(3); }});
        btnFour.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(4); }});
        btnFive.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(5); }});
        btnSix.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(6); }});
        btnSeven.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(7); }});
        btnEight.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(8); }});
        btnNine.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(9); }});
        
        btnPlus.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleEquals(1); }});
        btnMinus.setOnClickListener(new Button.OnClickListener() { public void onClick (View v) { handleEquals(2); }});
        btnMultiply.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleEquals(3); }});
        btnDivide.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleEquals(4); }});
        btnEquals.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleEquals(0); }});
        btnC.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ reset(); }});
        btnDecimal.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleDecimal(); }});
        btnPM.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v) { handlePlusMinus(); }});
        btnMC.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ memNum = 0; }});
        btnMR.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ setValue(Double.toString(memNum)); }});
        btnMM.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ memNum = memNum - Double.parseDouble(txtCalc.getText().toString()); operator = 0; }});
        btnMP.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ memNum = memNum + Double.parseDouble(txtCalc.getText().toString()); operator = 0; }});
        btnBS.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ handleBackspace();}});
        btnSqrRoot.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ setValue(Double.toString(Math.sqrt(Double.parseDouble(txtCalc.getText().toString())))); }});
        btnPerc.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ setValue(Double.toString(num * (0.01 * Double.parseDouble(txtCalc.getText().toString())))); }});        

        btnOff.setOnClickListener(new Button.OnClickListener()
        {
        		public void onClick (View v)
        		{ 
        			//setContentView(R.layout.additem);
        			//EditText money = (EditText) findViewById(R.id.money);
        			//money.setText(txtCalc.getText().toString().trim());
        			Intent i=new Intent();
        			Bundle b=new Bundle();
        			b.putString("money_cal", txtCalc.getText().toString().trim());
        			i.putExtras(b);
        			setResult(RESULT_OK,i);
        			finish();
        		}
        });
        //initScreenLayout();        
        //reset();
    }
    
    private void initScreenLayout()
    {
        //320 x 480 (Tall Display - HVGA-P) [default]
    	//320 x 240 (Short Display - QVGA-L)
        //240 x 320 (Short Display - QVGA-P) 
    	
    	DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        //this.showAlert(dm.widthPixels +" "+ dm.heightPixels, dm.widthPixels +" "+ dm.heightPixels, dm.widthPixels +" "+ dm.heightPixels, false);

        int height = dm.heightPixels;
        int width = dm.widthPixels;
        
        if (height < 400 || width < 300)
        {
        	txtCalc.setTextSize(22);        	
        }
        
        if (width < 300)
        {
        	btnMC.setTextSize(18);
        	btnMR.setTextSize(18);
        	btnMP.setTextSize(18);
        	btnMM.setTextSize(18);
        	btnBS.setTextSize(18);
        	btnDivide.setTextSize(18);
        	btnPlus.setTextSize(18);
        	btnMinus.setTextSize(18);
        	btnMultiply.setTextSize(18);
        	btnEquals.setTextSize(18);
        	btnPM.setTextSize(18);
        	btnPerc.setTextSize(18);
        	btnC.setTextSize(18);
        	btnSqrRoot.setTextSize(18);
        	btnNine.setTextSize(18);
        	btnEight.setTextSize(18);
        	btnSeven.setTextSize(18);
        	btnSix.setTextSize(18);
        	btnFive.setTextSize(18);
        	btnFour.setTextSize(18);
        	btnThree.setTextSize(18);
        	btnTwo.setTextSize(18);
        	btnOne.setTextSize(18);
        	btnZero.setTextSize(18);
        	btnDecimal.setTextSize(18);
        	
         	btnOff.setTextSize(18);
        }
        
        btnZero.setTextColor(0xFF3399FF);
        btnOne.setTextColor(0xFF3399FF);
        btnTwo.setTextColor(0xFF3399FF);
        btnThree.setTextColor(0xFF3399FF);
        btnFour.setTextColor(0xFF3399FF);
        btnFive.setTextColor(0xFF3399FF);
        btnSix.setTextColor(0xFF3399FF);
        btnSeven.setTextColor(0xFF3399FF);
        btnEight.setTextColor(0xFF3399FF);
        btnNine.setTextColor(0xFF3399FF);
        btnPM.setTextColor(0xFF3399FF);
        btnDecimal.setTextColor(0xFF3399FF);
        
        btnMP.setTextColor(0xAAAAAAAA);
        btnMM.setTextColor(0xAAAAAAAA);
        btnMR.setTextColor(0xAAAAAAAA);
        btnMC.setTextColor(0xAAAAAAAA);
        btnBS.setTextColor(0xAAAAAAAA);
        btnC.setTextColor(0xAAAAAAAA);
    	btnPerc.setTextColor(0xAAAAAAAA);
    	btnSqrRoot.setTextColor(0xAAAAAAAA);
    	
    	
    	
    	btnNine.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnEight.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnSeven.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnSix.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnFive.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnFour.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnThree.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnZero.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnTwo.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnOne.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	btnZero.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
    	
    	//btnZero.setBackgroundColor(0XFF202020);
    }
    
    private void initControls()
    {
    	/*
        txtCalc = (EditText)findViewById(R.id.txtCalc);
        btnZero = (Button)findViewById(R.id.btnZero);
        btnOne = (Button)findViewById(R.id.btnOne);
        btnTwo = (Button)findViewById(R.id.btnTwo);
        btnThree = (Button)findViewById(R.id.btnThree);
        btnFour = (Button)findViewById(R.id.btnFour);
        btnFive = (Button)findViewById(R.id.btnFive);
        btnSix = (Button)findViewById(R.id.btnSix);
        btnSeven = (Button)findViewById(R.id.btnSeven);
        btnEight = (Button)findViewById(R.id.btnEight);
        btnNine = (Button)findViewById(R.id.btnNine);
        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnMinus = (Button)findViewById(R.id.btnMinus);
        btnMultiply = (Button)findViewById(R.id.btnMultiply);
        btnDivide = (Button)findViewById(R.id.btnDivide);
        btnEquals = (Button)findViewById(R.id.btnEquals);
        btnC = (Button)findViewById(R.id.btnC);
        btnDecimal = (Button)findViewById(R.id.btnDecimal);
        btnMC = (Button)findViewById(R.id.btnMC);
        btnMR = (Button)findViewById(R.id.btnMR);
        btnMM = (Button)findViewById(R.id.btnMM);
        btnMP = (Button)findViewById(R.id.btnMP);
        btnBS = (Button)findViewById(R.id.btnBS);
        btnPerc = (Button)findViewById(R.id.btnPerc);
        btnSqrRoot = (Button)findViewById(R.id.btnSqrRoot);
        btnPM = (Button)findViewById(R.id.btnPM);
        
        btnOff = (Button)findViewById(R.id.off);  

        btnZero.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(0); }});
        btnOne.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(1); }});
        btnTwo.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(2); }});
        btnThree.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(3); }});
        btnFour.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(4); }});
        btnFive.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(5); }});
        btnSix.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(6); }});
        btnSeven.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(7); }});
        btnEight.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(8); }});
        btnNine.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleNumber(9); }});
        btnPlus.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleEquals(1); }});
        btnMinus.setOnClickListener(new Button.OnClickListener() { public void onClick (View v) { handleEquals(2); }});
        btnMultiply.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleEquals(3); }});
        btnDivide.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleEquals(4); }});
        btnEquals.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleEquals(0); }});
        btnC.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ reset(); }});
        btnDecimal.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ handleDecimal(); }});
        btnPM.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v) { handlePlusMinus(); }});
        btnMC.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ memNum = 0; }});
        btnMR.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ setValue(Double.toString(memNum)); }});
        btnMM.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ memNum = memNum - Double.parseDouble(txtCalc.getText().toString()); operator = 0; }});
        btnMP.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ memNum = memNum + Double.parseDouble(txtCalc.getText().toString()); operator = 0; }});
        btnBS.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ handleBackspace();}});
        btnSqrRoot.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ setValue(Double.toString(Math.sqrt(Double.parseDouble(txtCalc.getText().toString())))); }});
        btnPerc.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ setValue(Double.toString(num * (0.01 * Double.parseDouble(txtCalc.getText().toString())))); }});        
      
        btnOff.setOnClickListener(new Button.OnClickListener(){ public void onClick (View v){ finish();}});
        */
/*
        txtCalc.setOnKeyListener(new OnKeyListener()
        { 
        	public boolean onKey(View v, int i, KeyEvent e)
        	{
        		//if (e.isDown())
        		//{
        			int keyCode = e.getKeyCode();
        			
        			//txtCalc.append("["+Integer.toString(keyCode)+"]");
        			
    				switch (keyCode)
    				{
    					case KeyEvent.KEYCODE_0:
	    					handleNumber(0);
	    					break;
	    					
    					case KeyEvent.KEYCODE_1:
	    					handleNumber(1);
	    					break;
	    					
    					case KeyEvent.KEYCODE_2:
	    					handleNumber(2);
	    					break;
	    					
	    				case KeyEvent.KEYCODE_3: 
	    					handleNumber(3);
	    					break;
	    					
	    				case KeyEvent.KEYCODE_4: 
	    					handleNumber(4);
	    					break;
	    					
	    				case KeyEvent.KEYCODE_5: 
	    					handleNumber(5);
	    					break;
	    					
	    				case KeyEvent.KEYCODE_6: 
	    					handleNumber(6);
	    					break;
	    					
	    				case KeyEvent.KEYCODE_7: 
	    					handleNumber(7);
	    					break;
	    					
	    				case KeyEvent.KEYCODE_8: 
	    					handleNumber(8);
	    					break;
	    					
	    				case KeyEvent.KEYCODE_9: 
	    					handleNumber(9);
	    					break;
	    			
	    				case 43: 
	    					handleEquals(1);
	    					break;
	    					
	    				case KeyEvent.KEYCODE_EQUALS: 
	    					handleEquals(0);
	    					break;
	    				
	    				case KeyEvent.KEYCODE_MINUS: 
	    					handleEquals(2);
	    					break;
	    			
	    				case KeyEvent.KEYCODE_PERIOD:
	    					handleDecimal();
	    					break;
	    					
	    				case KeyEvent.KEYCODE_C: 
	    					reset();
	    					break;
	    					
	    				case KeyEvent.KEYCODE_SLASH:
	    	        		handleEquals(4);	    					
	    					break;
	    					
	    				case KeyEvent.KEYCODE_BACK:
	    	        		  finish();  					
	    					break;				
	    				case KeyEvent.KEYCODE_DPAD_DOWN:
	    					return false;
    				}
        		//}
        		
        		return true;
        	}
        });    */
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    menu.add(0 , MENU_EXIT, 1 ,R.string.menu_exit).setIcon(R.drawable.exit)
	    .setAlphabeticShortcut('E');

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId())
        {  
		   case MENU_EXIT:
		     		Intent intent = new Intent();
		       		intent.setClass(calc.this, additem.class);
	        		startActivity(intent);
	        		calc.this.finish();	  
		            break ;
		 }
	    
	    return true;
	}
    
    private void handleEquals(int newOperator)
    {
    	if (hasChanged)
    	{
	    	switch (operator)
	    	{
		    	case 1:
			    	num = num + Double.parseDouble(txtCalc.getText().toString());
					break;
		    	case 2:
		    		num = num - Double.parseDouble(txtCalc.getText().toString());
		    		break;
		    	case 3:
		    		num = num * Double.parseDouble(txtCalc.getText().toString());
		    		break;
		    	case 4:
		    		num = num / Double.parseDouble(txtCalc.getText().toString());
		    		break;
	    	}
	    	
	    	String txt = Double.toString(num);
	    	txtCalc.setText(txt);
	    	txtCalc.setSelection(txt.length());
	    	
			readyToClear = true;
			hasChanged = false;
    	}
    	
    	operator = newOperator;
    }
    
    private void handleNumber(int num)
    {
    	if (operator == 0)
    		reset();
    	
    	String txt = txtCalc.getText().toString();
		if (readyToClear)
		{
			txt = "";
			readyToClear = false;
		}
		else if (txt.equals("0"))
		{
			txt = "";
		}
		
		txt = txt + Integer.toString(num);
		
		txtCalc.setText(txt);
		//txtCalc.setSelection(txt.length());
		
		hasChanged = true;
    } 
    
    private void setValue(String value)
    {
    	if (operator == 0)
    		reset();
    	
    	if (readyToClear)
    	{
    		readyToClear = false;
    	}
    	
    	txtCalc.setText(value);
		txtCalc.setSelection(value.length());
		
		hasChanged = true;
    }
    
    private void handleDecimal()
    {
    	if (operator == 0)
    		reset();
    	
    	if (readyToClear)
    	{
    		txtCalc.setText("0.");
    		txtCalc.setSelection(2);
    		readyToClear = false;
    		hasChanged = true;
    	}
    	else 
    	{
    		String txt = txtCalc.getText().toString();
    		
    		if (!txt.contains("."))
    		{
    			txtCalc.append(".");
    			hasChanged = true;
    		}
    	}
    }
    
    private void handleBackspace()
    {
    	if (!readyToClear)
    	{
	    	String txt = txtCalc.getText().toString();
	    	if (txt.length() > 0)
	    	{
	    		txt = txt.substring(0, txt.length() - 1);
	    		if (txt.equals(""))
	    			txt = "0";
	    		
	    		txtCalc.setText(txt);
	    		txtCalc.setSelection(txt.length());
	    	}
    	}
    }
    
    private void handlePlusMinus()
    {
    	if (!readyToClear)
    	{
	    	String txt = txtCalc.getText().toString();
	    	if (!txt.equals("0"))
	    	{
	        	if (txt.charAt(0) == '-')
	        		txt = txt.substring(1, txt.length());
	        	else
	        		txt = "-" + txt;
	        	
	        	txtCalc.setText(txt);
	        	txtCalc.setSelection(txt.length());
	    	}
    	}
    }
    
    private void reset()
    {
		num = 0;
		txtCalc.setText("0");
		//txtCalc.setSelection(1);
		operator = 1;
    }
}