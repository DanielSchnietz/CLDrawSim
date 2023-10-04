package Football.Tournaments.ChampionsLeague

import Football.Tournaments.FootballCompetitionInfo

case class ChampionsLeagueInfo(override val groupPhasePosition: Int,
                               override val group: Char) extends FootballCompetitionInfo(groupPhasePosition, group)
