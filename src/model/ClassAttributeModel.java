package model;

import javafx.collections.*;
import javafx.beans.property.*;

import java.lang.reflect.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.io.Serializable;

public class ClassAttributeModel implements Serializable, Cloneable{
	
	public StringProperty name = new SimpleStringProperty();
	public IntegerProperty visibility = new SimpleIntegerProperty(Modifier.PUBLIC);
	public StringProperty type = new SimpleStringProperty("void");

	public BooleanProperty isMethod = new SimpleBooleanProperty(false);
	public ObservableList<ClassAttributeModel> parameters = FXCollections.observableArrayList();

	public StringProperty stringRepresentation = new SimpleStringProperty();



	public ClassAttributeModel(){
		this("unnamed");
	}

	public ClassAttributeModel(String attributes){
		setPropertiesFromString(attributes);
	}

	public void setPropertiesFromString(String attributeString){
		String normalized = attributeString.trim().replaceAll("^[\\s]*([+-])*[\\s]*([^:\\s(]*)[\\s]*(\\(.*\\))?[\\s]*:*[\\s]*([^\\s]*).*$", "$1\0$2\0$3\0$4");
	
		String[] tokens = normalized.split("\0", -1);
		
		if(tokens[0].equals("+"))
			this.visibility.setValue(Modifier.PUBLIC);
		else if(tokens[0].equals("-"))
			this.visibility.setValue(Modifier.PRIVATE);
		else if(tokens[0].equals(""))
			this.visibility.setValue(Modifier.PROTECTED);

		this.name.setValue(tokens[1]);

		this.parameters.clear();
		
		if(!tokens[2].equals("")){
			this.isMethod.setValue(true);
			String paramString = tokens[2].replaceAll("[()]*", "");
			List<String> params = Arrays.asList(paramString.split(","));
			
			for(String param : params){
				if(!param.equals(""))
					this.parameters.add(new ClassAttributeModel(param));
			}
				
		}
		else
			this.isMethod.setValue(false);
		

		this.type.setValue((tokens[3].equals("")) ? "void" : tokens[3]);
		updateStringRepresentation();
	}

	public void updateStringRepresentation(){
		stringRepresentation.setValue(getAsString());
	}

	public String getAsString(){
		String format = "";
		if(this.visibility.getValue() == Modifier.PUBLIC)
			format += "+";
		else if(this.visibility.getValue() == Modifier.PRIVATE)
			format += "-";

		format += " "+this.name.getValue();

		if(this.isMethod.getValue()){
			format += "(";

			if(this.parameters.size() > 0){
				for(ClassAttributeModel param : this.parameters)
					format += param.getAsParameterString() + ", ";

				format = format.substring(0, format.length() - 2);
			}
				
			format += ")";
		}

		format += " : "+this.type.getValue();

		return format;
	}

	public String getAsParameterString(){
		return this.name.getValue() + " : " + this.type.getValue();
	}

	public String getAsJavaString(){
		String format = "";
		if(this.visibility.getValue() == Modifier.PUBLIC)
			format += "public";
		else if(this.visibility.getValue() == Modifier.PRIVATE)
			format += "private";

		format += " " +this.type.getValue()+ " " +this.name.getValue();

		if(this.isMethod.getValue()){
			format += "(";

			if(this.parameters.size() > 0){
				for(ClassAttributeModel param : this.parameters)
					format += param.getAsJavaParameterString() + ", ";

				format = format.substring(0, format.length() - 2);
			}

			format += "){\n\n}";
		}
		else{
			format += ";";
		}
		return format;
	}

	public String getAsJavaParameterString(){
		return this.type.getValue()+ " " +this.name.getValue();
	}

	private void initialize(){
		name = new SimpleStringProperty();
		visibility = new SimpleIntegerProperty(Modifier.PUBLIC);
		type = new SimpleStringProperty("void");
	
		isMethod = new SimpleBooleanProperty(false);
		parameters = FXCollections.observableArrayList();
 		stringRepresentation = new SimpleStringProperty();
	}

	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException{
		initialize();
		name.setValue((String)stream.readObject());
		visibility.setValue(stream.readInt());
		type.setValue((String)stream.readObject());
		isMethod.setValue(stream.readBoolean());
		parameters.addAll((ArrayList)stream.readObject());
		updateStringRepresentation();
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException{
		stream.writeObject(name.getValue());
		stream.writeInt(visibility.intValue());
		stream.writeObject(type.getValue());
		stream.writeBoolean(isMethod.getValue());
		stream.writeObject(new ArrayList(parameters));
	}

	public ClassAttributeModel clone() throws CloneNotSupportedException{
		ClassAttributeModel clone = (ClassAttributeModel)super.clone();
		clone.name = new SimpleStringProperty(name.getValue());
		clone.visibility = new SimpleIntegerProperty(visibility.getValue());
		clone.type = new SimpleStringProperty(type.getValue()); 
		clone.isMethod = new SimpleBooleanProperty(isMethod.getValue());
		clone.parameters = FXCollections.observableArrayList();

		parameters.forEach((argument)->{
			try{
				clone.parameters.add(argument.clone());

			}
			catch(CloneNotSupportedException e){
				
			}
		});

		return clone;
	}
}