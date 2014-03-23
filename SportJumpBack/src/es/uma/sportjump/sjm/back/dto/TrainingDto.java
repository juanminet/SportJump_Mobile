package es.uma.sportjump.sjm.back.dto;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TrainingDto implements Serializable{
	
	private static final long serialVersionUID = 1088421740163680371L;
	
	private String name;
	private String type;
	private String description;
	
	private List<ExerciseDto> listBlock;
	
	
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

	public List<ExerciseDto> getListBlock() {
		return listBlock;
	}

	public void setListBlock(List<ExerciseDto> listBlock) {
		this.listBlock = listBlock;
	}

	
	public static TrainingDto objectfromJson(String json) {
		final Gson gson = new Gson();		
		final TrainingDto training = gson.fromJson(json, TrainingDto.class);

		return training;
	}

	public static List<TrainingDto> listfromJson(String json) {
		final Gson gson = new Gson();
		final Type trainingListType = new TypeToken<List<TrainingDto>>() {}.getType();
		final List<TrainingDto> trainingList = gson.fromJson(json, trainingListType);

		return trainingList;
	}

}
