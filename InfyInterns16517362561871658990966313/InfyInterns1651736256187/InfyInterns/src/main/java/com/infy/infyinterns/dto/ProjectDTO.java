package com.infy.infyinterns.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;



public class ProjectDTO {
	@NotNull(message = "{project.projectname.absent}")
	private Integer projectId;
	
	private String projectName;
	
	private Integer ideaOwner;

	@NotNull(message = "{project.releasedate.absent}")
	private LocalDate releaseDate;

	@NotNull(message = "{mentor.mentorid.absent}")
	@Valid
	private MentorDTO mentorDTO;

	public ProjectDTO() {
		super();
	}
	
	public ProjectDTO(Integer projectId, String projectName,
			Integer ideaOwner, LocalDate releaseDate,
			MentorDTO mentorDTO) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.ideaOwner = ideaOwner;
		this.releaseDate = releaseDate;
		this.mentorDTO = mentorDTO;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getIdeaOwner() {
		return ideaOwner;
	}

	public void setIdeaOwner(Integer ideaOwner) {
		this.ideaOwner = ideaOwner;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public MentorDTO getMentorDTO() {
		return mentorDTO;
	}

	public void setMentorDTO(MentorDTO mentorDTO) {
		this.mentorDTO = mentorDTO;
	}

}
