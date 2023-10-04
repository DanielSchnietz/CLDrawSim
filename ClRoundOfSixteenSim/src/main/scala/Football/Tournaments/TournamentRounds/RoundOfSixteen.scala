package Football.Tournaments.TournamentRounds

import Exporter.Exporter
import Football.SportsTeam
import Football.Tournaments.TournamentRound
import TournamentDraws.{DrawSimulator, DrawnRound, Validation}

/** A person who uses our application.
 *
 *  @constructor creates a new draw simulator instance.
 *  @param teams the list of teams participating in this round of the tournament.
 *  @param exporter the instance of the desired exporter.
 */
case class RoundOfSixteen[A <: Exporter](teams: List[SportsTeam], exporter: A) extends TournamentRound with DrawnRound {

  val drawSimulator: DrawSimulator = new DrawSimulator(teams, this, exporter: Exporter)

  /** Validates the given list of participants.
   *
   * Checks for empty lists and lists with missing participants for this round of the tournament.
   *
   * @param list List of given participants.
   * @return Returns true if all given validations are passed.
   */
  def participantValidation[B](list: List[B]): Boolean = {
    list.nonEmpty && list.length == 16
  }

  /** Holds the validation criteria and starts validation process.
   *
   * @param home     The current home team.
   * @param visiting The current visiting team.
   * @return Returns true if check of parameters (Validation.checkForDifferentParameters()) passes.
   */
  def drawValidationCriteria[B <: SportsTeam](home: B, visiting: B): Boolean = {
    Validation.checkForDifferentParameters((home.tournamentInfo.group, visiting.tournamentInfo.group),
      (home.tournamentInfo.groupPhasePosition, visiting.tournamentInfo.groupPhasePosition),
      (home.country, visiting.country))
  }
}
