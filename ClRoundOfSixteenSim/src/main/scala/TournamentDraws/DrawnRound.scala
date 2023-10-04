package TournamentDraws

import Football.SportsTeam

trait DrawnRound {
  val drawSimulator: DrawSimulator
  def participantValidation[A <: SportsTeam](list: List[A]): Boolean
  def drawValidationCriteria[A <: SportsTeam](home: A, visiting: A): Boolean
}
