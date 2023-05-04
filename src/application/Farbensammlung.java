package application;

import java.awt.Color;

public abstract class Farbensammlung {
			
	private static final Color cRot = Color.RED;
	private static final Color cBlau = Color.BLUE;
	private static final Color cGruen = Color.GREEN;
	private static final Color cPink = Color.PINK;
	private static final Color cGelb = Color.YELLOW;
	private static final Color cGrau = Color.GRAY;
	
	private static Color[] farbeList = {cRot,cBlau, cGruen,cPink, cGelb, cGrau};
	
	
	public static Color[] getFarben() {
		return farbeList;
	}
	
	public static String getColorName(Color c) {
		if(c == Color.RED) return "RED";
		if(c == Color.BLUE) return "BLUE";
		if(c == Color.GREEN) return "GREEN";
		if(c == Color.PINK) return "PINK";
		if(c == Color.YELLOW) return "YELLOW";
		if(c == Color.GRAY) return "GRAY";
		return null;
	}
}
