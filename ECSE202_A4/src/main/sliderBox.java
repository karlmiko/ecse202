
// Assignment 4 - ECSE202
// Karl Michel Koerich
// 260870321
// Fall 2018

package main;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import acm.gui.TableLayout;

//Class to help set up the sliders on the screen
public class sliderBox extends Component{

	JPanel myPanel;
	JLabel nameLabel;
	JLabel minLabel;
	JLabel maxLabel;
	JSlider mySlider;
	JLabel sReadout;
	Integer imin;
	Integer imax;

	public sliderBox(String name, Integer min, Integer dValue, Integer max) { // Integer values
		myPanel = new JPanel();
		nameLabel = new JLabel(name);
		minLabel = new JLabel(min.toString());
		maxLabel = new JLabel(max.toString());
		mySlider = new JSlider(min, max, dValue);
		sReadout = new JLabel(dValue.toString());
		sReadout.setForeground(Color.blue);
		myPanel.setLayout(new TableLayout(1, 5));
		myPanel.add(nameLabel, "width=80");
		myPanel.add(minLabel, "width=25");
		myPanel.add(mySlider, "width=80");
		myPanel.add(maxLabel, "width=80");
		myPanel.add(sReadout, "width=25");
		imin = min;
		imax = max;
	}

	// Get value from slider
	public Integer getISlider() {
		return mySlider.getValue();
	}

	//Set value to slider
	public void setISlider(int val) {
		mySlider.setValue(val);
		sReadout.setText(val + "");

	}
}