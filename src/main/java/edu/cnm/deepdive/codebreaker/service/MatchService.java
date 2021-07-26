package edu.cnm.deepdive.codebreaker.service;

import edu.cnm.deepdive.codebreaker.model.dao.MatchRepository;
import edu.cnm.deepdive.codebreaker.model.entity.Code;
import edu.cnm.deepdive.codebreaker.model.entity.Match;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

  private final MatchRepository repository;

  @Autowired
  public MatchService(MatchRepository repository) {
    this.repository = repository;
  }

  public Match start(Match match, User user) {
    match.setOriginator(user);
    match.getParticipants().add(user);
    List<Code> codes = match.getCodes();
    for (int i = 0; i < match.getCodesToGenerate(); i++) {
      Code code = new Code();
      code.setLength(match.getCodeLength());
      code.setPool(match.getPool());
      code.setMatch(match);
      // TODO Generate random code string, and invoke code.setText().
      codes.add(code);
    }
    return repository.save(match);
  }

  Stream<Match> getAvailableMatches(User user, Date cutoff, int codeLength, int poolSize) {
    return repository
        .findAllByParticipantsNotContainsAndEndingAfterAndCodeLengthAndPoolSizeOrderByEndingAsc(
            user, cutoff, codeLength, poolSize
        );
  }

  Stream<Match> getUserMatchesAfterCutoff(User user, Date cutoff) {
    return repository.findAllByParticipantsContainsAndEndingAfterOrderByEndingAsc(user, cutoff);
  }

  Stream<Match> getUserMatchesBeforeCutoff(User user, Date cutoff) {
    return repository.findAllByParticipantsContainsAndEndingBeforeOrderByEndingDesc(user, cutoff);
  }

  // etc.

}
