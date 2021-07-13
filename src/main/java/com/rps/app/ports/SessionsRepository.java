package com.rps.app.ports;

import com.rps.app.core.model.Session;
import java.util.Optional;

public interface SessionsRepository {

  Session create(Session session);

  Session update(Session session);

  Optional<Session> findById(String gameId);
}
