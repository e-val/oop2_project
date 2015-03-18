package model;

import javafx.beans.property.*;

import java.io.Serializable;

import common.*;

public class ViewModel implements Serializable, Cloneable{

	public DoubleProperty xPosition;

	public DoubleProperty yPosition;

	public DoubleProperty width;

	public DoubleProperty height;



	public Point attachPointTop;

	public Point attachPointRight;

	public Point attachPointBottom;

	public Point attachPointLeft;


	public ViewModel(){
		initialize();
		bind();
	}

	private void initialize(){
		xPosition = new SimpleDoubleProperty(0);
		yPosition = new SimpleDoubleProperty(0);
		width = new SimpleDoubleProperty(0);
		height = new SimpleDoubleProperty(0);


		attachPointTop = new Point();
		attachPointRight = new Point();
		attachPointBottom = new Point();
		attachPointLeft = new Point();


	}

	private void bind(){
		attachPointTop.x.bind(width.divide(2.0).add(xPosition));
		attachPointTop.y.bind(yPosition);

		attachPointRight.x.bind(xPosition.add(width));
		attachPointRight.y.bind(height.divide(2.0).add(yPosition));

		attachPointBottom.x.bind(width.divide(2.0).add(xPosition));
		attachPointBottom.y.bind(yPosition.add(height));

		attachPointLeft.x.bind(xPosition);
		attachPointLeft.y.bind(height.divide(2.0).add(yPosition));
	}

	public ViewModel clone() throws CloneNotSupportedException{
		ViewModel clone = new ViewModel();

		clone.xPosition.setValue(xPosition.getValue());
		clone.yPosition.setValue(yPosition.getValue());
		clone.width.setValue(width.getValue());
		clone.height.setValue(height.getValue());

		return clone;
	}

	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException{
		initialize();
		xPosition.setValue(stream.readDouble());
		yPosition.setValue(stream.readDouble());
		width.setValue(stream.readDouble());
		height.setValue(stream.readDouble());

		Point tmp = (Point)stream.readObject();
		attachPointTop.x.setValue(tmp.x.getValue());
		attachPointTop.y.setValue(tmp.y.getValue());

		tmp = (Point)stream.readObject();
		attachPointRight.x.setValue(tmp.x.getValue());
		attachPointRight.y.setValue(tmp.y.getValue());

		tmp = (Point)stream.readObject();
		attachPointBottom.x.setValue(tmp.x.getValue());
		attachPointBottom.y.setValue(tmp.y.getValue());

		tmp = (Point)stream.readObject();
		attachPointLeft.x.setValue(tmp.x.getValue());
		attachPointLeft.y.setValue(tmp.y.getValue());

		bind();
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException{
		stream.writeDouble(xPosition.getValue());
		stream.writeDouble(yPosition.getValue());
		stream.writeDouble(width.getValue());
		stream.writeDouble(height.getValue());

		stream.writeObject(attachPointTop);
		stream.writeObject(attachPointRight);
		stream.writeObject(attachPointBottom);
		stream.writeObject(attachPointLeft);
	}
}