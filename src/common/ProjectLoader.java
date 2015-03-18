package common;

import javax.tools.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import java.io.File;
import java.net.URLClassLoader;
import java.net.URL;
import java.net.URI;

import java.lang.reflect.*;

import javafx.event.*;

import model.*;

public class ProjectLoader {
	private JavaCompiler.CompilationTask compileTask;
	private File compileDir = new File("tmpCompile");
	private List<ClassModel> classModels = new ArrayList<ClassModel>();

	public ProjectLoader(String path){

		try{
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

			compileDir.mkdirs();
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(compileDir));
			

			List<File> sourceFiles = findAllSourceFilesInDirectory(new File(path));

			Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjectsFromFiles(sourceFiles);
			
			compileTask = compiler.getTask(null, fileManager, null, Arrays.asList("-parameters"), null, fileObjects);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private List<File> findAllSourceFilesInDirectory(File directory){
		
		List<File> foundSourceFiles = new ArrayList<File>();
		List<File> contents = Arrays.asList(directory.listFiles());
		
		
		contents.forEach(file->{
			
			if(file.isDirectory()){

				foundSourceFiles.addAll(findAllSourceFilesInDirectory(file));
			}
			else if(file.isFile() && file.getAbsolutePath().matches(".*\\.java$")){
				foundSourceFiles.add(file);
			}
		});

		return foundSourceFiles;
	}

	private List<File> findAllClassFilesInDirectory(File directory){
		
		List<File> foundSourceFiles = new ArrayList<File>();
		List<File> contents = Arrays.asList(directory.listFiles());
		
		
		contents.forEach(file->{
			
			if(file.isDirectory()){

				foundSourceFiles.addAll(findAllClassFilesInDirectory(file));
			}
			else if(file.isFile() && file.getAbsolutePath().matches(".*\\.class$")){
				foundSourceFiles.add(file);
			}
		});

		return foundSourceFiles;
	}

	public void startLoadingOnNewThread(EmptyCallBack onCompileComplete){
		new Thread(()->{
			try{
				compileTask.call();
				List<File> classFiles = findAllClassFilesInDirectory(compileDir);
				URLClassLoader classLoader = new URLClassLoader(new URL[]{compileDir.toURI().toURL()});

				classFiles.forEach(file->{
					try{
						String className = file.getAbsolutePath().replaceAll(compileDir.getAbsolutePath()+"/", "").replaceAll(File.separator, ".").replaceAll("\\.class$", "");
						classModels.add(getClassModelFromClass(classLoader.loadClass(className)));
					}
					catch(Exception e){
						e.printStackTrace();
					}
				});
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			onCompileComplete.call();



		}).start();
	}

	private ClassModel getClassModelFromClass(Class c){
		ClassModel model = new ClassModel();
		List<Field> fields = Arrays.asList(c.getDeclaredFields());
		model.typeName.setValue(c.getName());
		fields.forEach(field->{
			ClassAttributeModel attr = new ClassAttributeModel();
			attr.name.setValue(field.getName());
			attr.type.setValue(field.getGenericType().getTypeName());
			
			attr.visibility.setValue(((field.getModifiers() & Modifier.PUBLIC) | (field.getModifiers() & Modifier.PRIVATE) | (field.getModifiers() & Modifier.PROTECTED)));
			attr.updateStringRepresentation();
			model.attributes.add(attr);
		});

		List<Method> methods = Arrays.asList(c.getDeclaredMethods());

		methods.forEach(method->{
			ClassAttributeModel attr = new ClassAttributeModel();
			attr.name.setValue(method.getName());
			attr.visibility.setValue(((method.getModifiers() & Modifier.PUBLIC) | (method.getModifiers() & Modifier.PRIVATE) | (method.getModifiers() & Modifier.PROTECTED)));
			attr.type.setValue(method.getGenericReturnType().getTypeName());
			attr.isMethod.setValue(true);
			List<Parameter> parameters = Arrays.asList(method.getParameters());
			
			parameters.forEach(param->{
				ClassAttributeModel parameter = new ClassAttributeModel();
				parameter.name.setValue(param.getName());
				parameter.type.setValue(param.getParameterizedType().getTypeName());
				parameter.updateStringRepresentation();
				attr.parameters.add(parameter);
			});
			
			attr.updateStringRepresentation();
			model.methods.add(attr);
		});

		return model;
	}

	public List<ClassModel> getClassModels(){
		return classModels;
	}

}