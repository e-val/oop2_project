package view;

import javafx.scene.shape.*;
import javafx.beans.property.*;

import common.*;

public class Arrow extends Path{
	
	Point start = new Point();
	Point end = new Point();


	public void bindStart(DoubleProperty x, DoubleProperty y){
		start.x.bind(x);
		start.y.bind(y);
	}

	public void bindEnd(DoubleProperty x, DoubleProperty y){
		end.x.bind(x);
		end.y.bind(y);
	}
}