package Exporter

import TournamentDraws.MatchProbabilitySummary.DrawProbabilitySummary

class ConsoleExporter() extends Exporter {

  private def printString(string: String): Unit = {
    println(string)
  }

  private def printCollection(collection: Iterable[_]): Unit = {
    collection match {
      case map: DrawProbabilitySummary => map.foreach(println(_))
      case _ => //intentionally left blank
    }
  }

  override def exportData[A](data: A): Unit = {
    data match {
      case string: String => printString(string)
      case collection: Iterable[A]  => printCollection(collection)
    }
  }
}
