package model;
import javafx.collections.*;
import javafx.beans.property.*;

import java.io.Serializable;
import java.io.IOException;

import java.util.ArrayList;

import view.*;
import controller.*;

public class ClassModel extends TypeModel implements Serializable, Cloneable{

	public ViewModel viewModel = new ViewModel();

	public ObservableList<ClassAttributeModel> attributes = FXCollections.observableArrayList();

	public ObservableList<ClassAttributeModel> methods = FXCollections.observableArrayList();

	public ObjectProperty<ClassModel> extending = new SimpleObjectProperty<ClassModel>();

	public ObservableList<ClassModel> implementing = FXCollections.observableArrayList();

	public ObservableList<ClassModel> aggregations = FXCollections.observableArrayList();

	public ClassModel(){
		typeName.setValue("UnnamedClass");
	}

	private void initialize(){
		viewModel = new ViewModel();

		attributes = FXCollections.observableArrayList();

		methods = FXCollections.observableArrayList();

		extending = new SimpleObjectProperty<ClassModel>();

		implementing = FXCollections.observableArrayList();

		aggregations = FXCollections.observableArrayList();
	}

	public ClassModel clone() throws CloneNotSupportedException{
		ClassModel clone = (ClassModel)super.clone();

		clone.viewModel = viewModel.clone();
		clone.attributes = FXCollections.observableArrayList();
		clone.methods = FXCollections.observableArrayList();
		clone.extending = new SimpleObjectProperty<ClassModel>();
		clone.implementing = FXCollections.observableArrayList();
		clone.aggregations = FXCollections.observableArrayList();

		attributes.forEach((attribute)->{
			try{
				clone.attributes.add(attribute.clone());
			}
			catch(CloneNotSupportedException e){
				
			}
		});

		methods.forEach((method)->{
			try{
				clone.methods.add(method.clone());
			}
			catch(CloneNotSupportedException e){
				
			}
		});

		return clone;
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
		initialize();
		viewModel = (ViewModel)stream.readObject();
		attributes.addAll((ArrayList)stream.readObject());
		methods.addAll((ArrayList)stream.readObject());
		extending.setValue((ClassModel)stream.readObject());
		implementing.addAll((ArrayList)stream.readObject());
		aggregations.addAll((ArrayList)stream.readObject());
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException{
		stream.writeObject(viewModel);
		stream.writeObject(new ArrayList(attributes));
		stream.writeObject(new ArrayList(methods));
		stream.writeObject(extending.getValue());
		stream.writeObject(new ArrayList(implementing));
		stream.writeObject(new ArrayList(aggregations));
	}

	public String getAsJavaString(){
		String format = "public class " + typeName.getValue();
		if(this.extending.getValue() != null)
			format += " extends " + this.extending.getValue().typeName.getValue();
		
		format += "{\n";

		for(ClassAttributeModel attr : this.attributes)
			format += attr.getAsJavaString()+"\n";

		format += "\n";

		for(ClassAttributeModel method : this.methods)
			format += method.getAsJavaString()+"\n";

		format += "}";

		return format;
	}

	public String toString(){
		return "[ClassModel: name="+typeName.getValue()+", attributes="+attributes+", methods="+methods+"]";
	}
}