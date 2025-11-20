package com.CourseWorld.CourseWorld.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CourseWorld.CourseWorld.Component.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

}
