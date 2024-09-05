package com.infy.infyinterns.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.infyinterns.dto.MentorDTO;
import com.infy.infyinterns.dto.ProjectDTO;
import com.infy.infyinterns.entity.Mentor;
import com.infy.infyinterns.entity.Project;
import com.infy.infyinterns.exception.InfyInternException;
import com.infy.infyinterns.repository.MentorRepository;
import com.infy.infyinterns.repository.ProjectRepository;

import jakarta.transaction.Transactional;

@Service(value = "projectService")
@Transactional
public class ProjectAllocationServiceImpl implements ProjectAllocationService {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	MentorRepository mentorRepository;

	@Override
	public Integer allocateProject(ProjectDTO project) throws InfyInternException {

		Mentor mentor = mentorRepository.findById(project.getMentorDTO().getMentorId())
		.orElseThrow(() -> new InfyInternException("Service.MENTOR_NOT_FOUND"));
		

		if (mentor.getNumberOfProjectsMentored() >= 3){
			throw new InfyInternException("Service.CANNOT_ALLOCATE_PROJECT");
		}

		Project projectDetails = new Project();
		projectDetails.setIdeaOwner(project.getIdeaOwner());
		projectDetails.setProjectId(project.getProjectId());
		projectDetails.setProjectName(project.getProjectName());
		projectDetails.setReleaseDate(project.getReleaseDate());

		projectDetails.setMentor(mentor);
		mentor.setNumberOfProjectsMentored(mentor.getNumberOfProjectsMentored() + 1);
		projectRepository.save(projectDetails);


		return projectDetails.getProjectId();
	}

	
	@Override
	public List<MentorDTO> getMentors(Integer numberOfProjectsMentored) throws InfyInternException {
		List<Mentor> mentor = mentorRepository.findByNumberOfProjectsMentored(numberOfProjectsMentored);
		if (mentor.isEmpty()) {
			throw new InfyInternException("Service.NO_MENTORS_FOUND");
		}
		List<MentorDTO> list = new ArrayList<>();
		for (Mentor m : mentor) {
			MentorDTO mentorDTO = new MentorDTO();
			mentorDTO.setMentorId(m.getMentorId());
			mentorDTO.setMentorName(m.getMentorName());
			mentorDTO.setNumberOfProjectsMentored(m.getNumberOfProjectsMentored());
			list.add(mentorDTO);
		}
		return list;
	}


	@Override
	public void updateProjectMentor(Integer projectId, Integer mentorId) throws InfyInternException {
		Mentor mentor = mentorRepository.findById(mentorId)
		.orElseThrow(() -> new InfyInternException("Service.MENTOR_NOT_FOUND"));

		if (mentor.getNumberOfProjectsMentored() >= 3){
			throw new InfyInternException("Service.CANNOT_ALLOCATE_PROJECT");
		}
		Project project = projectRepository.findById(projectId)
		.orElseThrow(() -> new InfyInternException("Service.PROJECT_NOT_FOUND"));
		project.setMentor(mentor);
		mentor.setNumberOfProjectsMentored(mentor.getNumberOfProjectsMentored() + 1);
	}

	@Override
	public void deleteProject(Integer projectId) throws InfyInternException {
		Project project = projectRepository.findById(projectId)
		.orElseThrow(() -> new InfyInternException("Service.PROJECT_NOT_FOUND"));
		if (project == null ){
			projectRepository.deleteById(projectId);
		}else{
			project.getMentor().setNumberOfProjectsMentored(project.getMentor().getNumberOfProjectsMentored() - 1);
			project.setMentor(null);
			projectRepository.deleteById(projectId);
		}
		
	}
}