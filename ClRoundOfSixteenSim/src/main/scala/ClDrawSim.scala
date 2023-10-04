import Exporter.ConsoleExporter
import Football.FootballTeam
import Football.Tournaments.ChampionsLeague.{ChampionsLeague, ChampionsLeagueInfo}
import Football.Tournaments.TournamentRounds.RoundOfSixteen
import TournamentDraws.Countries

object ClDrawSim {
  private val consoleExporter: ConsoleExporter = new ConsoleExporter
  def main(args: Array[String]): Unit = {
    ChampionsLeague[RoundOfSixteen[ConsoleExporter]](RoundOfSixteen(
      ClRoundOfSixteenClubs.clubs,
      consoleExporter)).startSimulation(100000)
  }
}

object ClRoundOfSixteenClubs {
  val clubs: List[FootballTeam] = List(
    FootballTeam("SSC Neapel", Countries.ITA, ChampionsLeagueInfo(1, 'A')),
    FootballTeam("FC Liverpool", Countries.ENG, ChampionsLeagueInfo(2, 'A')),
    FootballTeam("FC Porto", Countries.POR, ChampionsLeagueInfo(1, 'B')),
    FootballTeam("FC Brügge", Countries.BEL, ChampionsLeagueInfo(2, 'B')),
    FootballTeam("FC Bayern", Countries.GER, ChampionsLeagueInfo(1, 'C')),
    FootballTeam("Inter Milan", Countries.ITA, ChampionsLeagueInfo(2, 'C')),
    FootballTeam("Tottenham", Countries.ENG, ChampionsLeagueInfo(1, 'D')),
    FootballTeam("Eintracht Frankfurt", Countries.GER, ChampionsLeagueInfo(2, 'D')),
    FootballTeam("FC Chelsea", Countries.ENG, ChampionsLeagueInfo(1, 'E')),
    FootballTeam("Milan", Countries.ITA, ChampionsLeagueInfo(2, 'E')),
    FootballTeam("Real Madrid", Countries.ESP, ChampionsLeagueInfo(1, 'F')),
    FootballTeam("RB Leipzig", Countries.GER, ChampionsLeagueInfo(2, 'F')),
    FootballTeam("Manchester City", Countries.ENG, ChampionsLeagueInfo(1, 'G')),
    FootballTeam("Borussia Dortmund", Countries.GER, ChampionsLeagueInfo(2, 'G')),
    FootballTeam("Benfica Lissabon", Countries.POR, ChampionsLeagueInfo(1, 'H')),
    FootballTeam("Paris Saint-Germain", Countries.FRA, ChampionsLeagueInfo(2, 'H')),
  )
}
