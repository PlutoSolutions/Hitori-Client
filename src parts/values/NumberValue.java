package client.values;

import client.module.Module;

import java.math.BigDecimal;
import java.util.ArrayList;

public class NumberValue {
	private String name;
	private double value;
	private double max;
	private double min;
	private Module parent;
	private String mode;

	public NumberValue(String name, double value, double min, double max) {
		this.name = name;
		this.value = value;
		this.max = max;
		this.min = min;
	}



	public NumberValue() {

	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setValue(double newValue) {
		this.value = newValue;
		if (newValue < this.getMin()) {
			this.value = this.getMin();
		}
	}

	public double getValue() {
		return this.round(this.value, 2);
	}
	public Module getParentMod(){
		return parent;
	}


	public double getMin() {
		return this.min;
	}

	public double getMax() {
		return this.max;
	}

	public void increase() {
		if (this.round(this.value, 2) < this.max) {
			this.value += 1.0;
		}
	}

	public void decrease() {
		if (this.round(this.value, 2) > this.min) {
			this.value -= 1.0;
		}
	}

    private double round(double doubleValue, int numOfDecimals)  {
        BigDecimal bigDecimal = new BigDecimal(doubleValue);
        bigDecimal = bigDecimal.setScale(numOfDecimals, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
	public boolean isCombo(){
		return this.mode.equalsIgnoreCase("Combo") ? true : false;
	}

	public boolean isCheck(){
		return this.mode.equalsIgnoreCase("Check") ? true : false;
	}

	public boolean isSlider(){
		return this.mode.equalsIgnoreCase("Slider") ? true : false;
	}


}
