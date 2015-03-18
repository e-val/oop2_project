package model;

import javafx.collections.*;
import javafx.beans.property.*;

import java.io.Serializable;

public class TypeModel implements Serializable, Cloneable{
	public static final TypeModel VOID_TYPE = new TypeModel("void");

	public StringProperty typeName = new SimpleStringProperty();

	public TypeModel(){
		this("UnnamedType");
	}

	public TypeModel(String name){
		typeName.setValue(name);
	}

	public TypeModel clone() throws CloneNotSupportedException {
		TypeModel clone = (TypeModel)super.clone();
		clone.typeName = new SimpleStringProperty(typeName.getValue()); 

		return clone;
	}


	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException{
		typeName = new SimpleStringProperty();
		typeName.setValue((String)stream.readObject());
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException{
		stream.writeObject(typeName.getValue());
	}
}