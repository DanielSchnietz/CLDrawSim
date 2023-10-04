package Football.Tournaments.ChampionsLeague

import Football.Tournaments.FootballTournament
import TournamentDraws.DrawnRound

/** Serves the startSimulation method to start a new draw.
 *
 *  @param round the current round of the competition e.g. RoundOfSixteen
 */
case class ChampionsLeague[A <: DrawnRound](round: A) extends FootballTournament(round) {

  /** Entry point for simulation.
   *
   * @param iterations The amount of iterations to divide by.
   */
  override def startSimulation(iterations: Int): Unit = {
    round.drawSimulator.simulate(iterations)
  }
}

