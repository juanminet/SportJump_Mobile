package es.uma.sportjump.sjm.back.dto;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ExerciseDto implements Serializable{
	
	
	private static final long serialVersionUID = 3838332162042670155L;
	
	private Long id;
	private String name;
	private String type;
	private String description;
	
	private List<String> listExercise;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getListExercise() {
		return listExercise;
	}

	public void setListExercise(List<String> listExercise) {
		this.listExercise = listExercise;
	}
	

	public static ExerciseDto objectfromJson(String json) {
		final Gson gson = new Gson();		
		final ExerciseDto exercise = gson.fromJson(json, ExerciseDto.class);

		return exercise;
	}

	public static List<ExerciseDto> listfromJson(String json) {
		final Gson gson = new Gson();
		final Type exerciseListType = new TypeToken<List<ExerciseDto>>() {}.getType();
		final List<ExerciseDto> exerciseList = gson.fromJson(json, exerciseListType);

		return exerciseList;
	}

}
