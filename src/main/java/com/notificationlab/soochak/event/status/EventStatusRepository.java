package com.notificationlab.soochak.event.status;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EventStatusRepository extends CrudRepository<EventStatusEntity, Long> {}
