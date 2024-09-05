package com.infy.infyinterns.repository;

import org.springframework.data.repository.CrudRepository;

import com.infy.infyinterns.entity.Mentor;
import java.util.List;



public interface MentorRepository extends CrudRepository<Mentor, Integer>
{
    // add methods if required
    List<Mentor> findByNumberOfProjectsMentored(Integer numberOfProjectsMentored);
}
