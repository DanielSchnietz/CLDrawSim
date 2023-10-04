package TournamentDraws

import Exporter.Exporter
import Football.SportsTeam
import TournamentDraws.MatchProbabilitySummary.DrawSummary

import scala.annotation.tailrec
import scala.util.Random

/** A draw simulator to simulate a given amount of draws in tournament scenarios.
 * It prints the probability of every match and the amount of invalid draws to the console.
 *
 *  @constructor create a new draw with the list of clubs going into the given round.
 *  @param completeTeamList the list of clubs finishing group phase 1st and 2nd
 *  @param competitionRound implementation of a DrawnRound which hands over validation methods.
 *                          E.G. RoundOfSixteen object from ChampionsLeague Class.
 *  @param exporter the instance of the desired exporter.
 */
class DrawSimulator(completeTeamList: List[SportsTeam], competitionRound: DrawnRound, exporter: Exporter) {

  private var summary: DrawSummary = Map.empty
  private var summaryUpdate: DrawSummary = Map.empty
  private var invalidDrawCount: Int = 0

  /** Starts the simulation of the draw.
   *
   *  Creates match-ups between the available teams.
   *  Simulates the given amount of draws and calculates the probability of every match for each team.
   *  Logs results to the console after finishing the simulation.
   *  @param iterations Number of iterations in this simulation.
   */
  def simulate(iterations: Int): Unit = {
    if(!competitionRound.participantValidation(completeTeamList)) {
      throw new Error(s"Given List of participants $completeTeamList is not valid. Please provide a valid list.")
    }
    else {
      (1 to iterations).foreach(_ => createMatchUps(completeTeamList))

      exporter.exportData(MatchProbabilitySummary.convertCountToProbability(summary, iterations))
      exporter.exportData(
        s"Invalid draws: ${MatchProbabilitySummary.convertCountToProbability(invalidDrawCount, iterations)}"
      )
    }
  }

  /** Checks if the draw cycle is completed.
   *
   *  Cycle is considered completed of there are no remaining participants that haven't got
   *  matched up with another participant.
   *  @param teamList takes a List() of available teams of type SportsTeam.
   *  @return Returns true if given list of available participants is empty.
   */
  private def cycleCompleted(teamList: List[SportsTeam]): Boolean = {
    teamList.isEmpty
  }

  /** Updates the summary var of class DrawSimulator.
   *
   *  Updates the summary by updating old values and adding values that are not present yet.
   *  Updating is done by the updatedWith method from MapOps package for every key and every value inside inner
   *  and outer maps.
   *
   */
  private def updateSummary(): Unit = {
    summaryUpdate.foreach(hashMap => {
      hashMap._2.foreach(valueMap => {
        summary = summary.updatedWith(hashMap._1)({
          case Some(map) => Some(map.updatedWith(valueMap._1)({ case Some(count) =>
            Some(count + valueMap._2) case None => Some(valueMap._2) }))
          case None => Some(Map(valueMap._1 -> valueMap._2))
        })
      })
    })
  }

  /** Stores the updates for the summary var until validation of the draw cycle.
   *
   * Stores the summaryUpdate by updating old values and adding values that are not present yet.
   * Updating is done by the updatedWith method from MapOps package for every key and every value inside inner
   * and outer maps.
   *
   */
  private def storeSummaryUpdate(home: String, visiting: String, increment: Int = 1): Unit = {
    summaryUpdate = summaryUpdate.updatedWith(home)({
      case Some(map) => Some(map.updatedWith(visiting)({ case Some(count) =>
        Some(count + increment) case None => Some(increment)}))
      case None => Some(Map(visiting -> increment))
    })
  }

  /** Creates match-ups between the given participants.
   *
   *  Draws a random participant as home and removes it from the current list.
   *  Determines eligible opponents for that home participant by checking given criteria from
   *  the competitionRound param of class DrawSimulator.
   *  If there are no eligible opponents, the cycle is marked as invalid and a new cycle is started.
   *  If there are eligible opponents, a random opponent is drawn and removed from the current list of available
   *  participants
   *  @param teamList represents the current List() of available participants for the current draw cycle.
   */
  @tailrec
  private def createMatchUps(teamList: List[SportsTeam]): Unit = {

    val (homeTeam: SportsTeam, teamsListWithoutHome) = drawTeam(teamList)
    val eligibleClubs: List[SportsTeam] = determineEligibleOpponents(homeTeam, teamsListWithoutHome)

    if(eligibleClubs.isEmpty) markDrawInvalid() else {
      val (visitingTeam: SportsTeam, _: List[SportsTeam]) = drawTeam(eligibleClubs)
      storeSummaryUpdate(homeTeam.name, visitingTeam.name)
      storeSummaryUpdate(visitingTeam.name, homeTeam.name)
      if (!cycleCompleted(removeTeamFromList(visitingTeam, teamsListWithoutHome)))
        createMatchUps(removeTeamFromList(visitingTeam, teamsListWithoutHome))
      else {
        updateSummary()
        summaryUpdate = Map.empty
      }
    }
  }

  /** Draws a random team for this draw cycle.
   *
   * Draws a random participant as home and removes it from the current list.

   * @param teamsList represents the current List() of available participants for the current draw cycle.
   * @return Returns a tuple of the drawn team and the updated list of available participants without
   *         the current drawn team.
   */
  private def drawTeam(teamsList: List[SportsTeam]): (SportsTeam, List[SportsTeam]) = {
    val random: Random = Random
    val drawnTeam: SportsTeam = teamsList(random.nextInt(teamsList.length))
    (drawnTeam, removeTeamFromList(drawnTeam, teamsList))
  }

  /** Determines eligible opponents for the current home team.
   *
   * Filters all eligible opponents for current home team.
   *
   * @param homeTeam represents the current drawn team as home team.
   * @param teamsList represents the current List() of available participants for the current draw cycle.
   * @return Returns a list of eligible opponents based on defined criteria.
   */
  private def determineEligibleOpponents(homeTeam: SportsTeam, teamsList: List[SportsTeam]): List[SportsTeam] = {
    teamsList.filter(visitingTeam => checkDrawPrinciples(homeTeam, visitingTeam))
  }

  /** Helper method to remove a given team from the teamsList.
   *
   * @param team team to remove from the current list.
   * @param participants a list of all available participants of the current cycle.
   * @return returns updated teamsList without currently drawn team.
   *
   */
  private def removeTeamFromList(team: SportsTeam, participants: List[SportsTeam]): List[SportsTeam] = {
    participants.filter(_ != team)
  }

  /** Determines eligible opponents for the current home team.
   *
   * Filters all eligible opponents for current home team.
   *
   * @param home represents the current drawn team as home team.
   * @param visiting represents the current drawn team as visiting team.
   * @return Returns true if all pre defined criteria from the given tournament round are matched.
   */
  private def checkDrawPrinciples(home: SportsTeam, visiting: SportsTeam): Boolean = {
    competitionRound.drawValidationCriteria(home, visiting)
  }

  /** Helper method to mark current draw cycle as invalid.
   *
   * Increases invalidDrawCount and clears summaryUpdate var.
   *
   */
  private def markDrawInvalid(): Unit = {
    invalidDrawCount += 1
    summaryUpdate = Map.empty
  }
}

