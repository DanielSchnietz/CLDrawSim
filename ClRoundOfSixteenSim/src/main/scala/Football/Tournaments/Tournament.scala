package Football.Tournaments

import TournamentDraws.DrawnRound

abstract class Tournament[A <: DrawnRound]() {
  def startSimulation(iterations: Int): Unit
}
