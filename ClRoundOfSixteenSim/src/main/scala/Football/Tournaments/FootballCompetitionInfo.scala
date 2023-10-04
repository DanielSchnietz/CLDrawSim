package Football.Tournaments

import Football.CompetitionInfo

class FootballCompetitionInfo(override val groupPhasePosition: Int,
                              override val group: Char) extends CompetitionInfo(groupPhasePosition, group)
