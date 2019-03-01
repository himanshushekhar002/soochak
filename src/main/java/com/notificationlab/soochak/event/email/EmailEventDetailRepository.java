package com.notificationlab.soochak.event.email;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailEventDetailRepository extends CrudRepository<EmailEventDetail, Long>{}
