package common;

import javafx.beans.property.*;

import java.io.Serializable;

public class Point implements Serializable{
	public DoubleProperty x;
	public DoubleProperty y;

	public Point(){
		initialize();
	}

	public void bind(DoubleProperty x, DoubleProperty y){
		this.x.bindBidirectional(x);
		this.y.bindBidirectional(y);
	}

	private void initialize(){
		x = new SimpleDoubleProperty(0);
		y = new SimpleDoubleProperty(0);
	}

	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException{
		initialize();

		x.setValue(stream.readDouble());
		y.setValue(stream.readDouble());
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException{
		stream.writeDouble(x.getValue());
		stream.writeDouble(y.getValue());
	}
}