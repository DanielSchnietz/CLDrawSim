package TournamentDraws

object Validation {

  /** Checks a pair of parameters for differences.
   *
   * @param paramPair takes a sequence of parameter pairs inside a tuple
   * @return Returns true if each pair of parameters is not equal
   *
   */
  def checkForDifferentParameters[A](paramPair: (A, A)*): Boolean = {
    paramPair.map(param => param._1 != param._2).forall(_ == true)
  }
}
