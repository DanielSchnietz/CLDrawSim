package Football

import Football.Tournaments.FootballCompetitionInfo
import TournamentDraws.Countries.Country

case class FootballTeam(override val name: String,
                        override val country: Country,
                        override val tournamentInfo: FootballCompetitionInfo)
  extends SportsTeam(name, country, tournamentInfo) {}
