package Football.Tournaments

import TournamentDraws.DrawnRound

class FootballTournament[A <: DrawnRound](round: A) extends Tournament() {
  def startSimulation(iterations: Int): Unit = {
    round.drawSimulator.simulate(iterations)
  }
}
