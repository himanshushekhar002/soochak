package com.notificationlab.soochak.email.smtp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmtpConfigRepository extends CrudRepository<SmtpConfiguration, Long>{}
