package ru.n5y.homework.wk.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.n5y.homework.wk.model.Player;

@Repository
public interface PlayersRepository extends CrudRepository<Player, String> {

}
