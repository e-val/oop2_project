package view;

import javafx.scene.shape.*;
import javafx.beans.property.*;
import javafx.beans.value.*;

import javafx.geometry.Point2D;
import javafx.scene.transform.*;


import common.*;



public class ExtendsArrow extends Arrow{


	private ChangeListener positionChangeListener = ((property, oldValue, newValue)->{
		updateArrow();
	});	

	public ExtendsArrow(){
		
		start.x.addListener(positionChangeListener);
		start.y.addListener(positionChangeListener);

		end.x.addListener(positionChangeListener);
		end.y.addListener(positionChangeListener);

		updateArrow();
	}


	private void updateArrow(){
		getElements().clear();

		Point2D arrowOne = new Point2D(0, 0);
		Point2D arrowTwo = new Point2D(0, 20);
		Point2D arrowThree = new Point2D(20, 0);
		

		Point2D arrowAngle = new Point2D(1, 1);



		Point2D wantedAngle = new Point2D(start.x.getValue(), start.y.getValue()).subtract(end.x.getValue(), end.y.getValue());

		double angleToRotate = arrowAngle.angle(wantedAngle);

		if(wantedAngle.getX() > wantedAngle.getY())
			angleToRotate *= -1;
		
		Rotate rotateTransform = new Rotate(angleToRotate);
		Translate translateTransform = new Translate(end.x.getValue(), end.y.getValue());

		Transform transform = translateTransform.createConcatenation(rotateTransform);

		arrowOne = transform.transform(arrowOne);
		arrowTwo = transform.transform(arrowTwo);
		arrowThree = transform.transform(arrowThree);


		Point2D lineStart = arrowTwo.midpoint(arrowThree);
		Point2D lineEnd = new Point2D(start.x.getValue(), start.y.getValue());


		getElements().add(new MoveTo(lineEnd.getX(), lineEnd.getY()));
		getElements().add(new LineTo(lineStart.getX(), lineStart.getY()));

		getElements().add(new MoveTo(arrowOne.getX(), arrowOne.getY()));
		getElements().add(new LineTo(arrowTwo.getX(), arrowTwo.getY()));
		getElements().add(new LineTo(arrowThree.getX(), arrowThree.getY()));
		getElements().add(new LineTo(arrowOne.getX(), arrowOne.getY()));

	}

}