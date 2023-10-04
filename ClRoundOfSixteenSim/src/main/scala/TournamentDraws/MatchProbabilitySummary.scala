package TournamentDraws

object MatchProbabilitySummary {

  type DrawProbabilitySummary = Map[String, Map[String, String]]
  type DrawSummary = Map[String, Map[String, Double]]

  /** Helper method to control precision of values.
   *
   * @param value The value to be rounded.
   * @param precision The amount of floats.
   * @return Returns the modified floating point number.
   */
  private def roundToPrecision(value: Double, precision: Int = 1): Double = {
    BigDecimal(value).setScale(precision, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  /** Converts a double value to a percentage by dividing by given number of iterations.
   *
   * @param summary  The source for the count to be divided by iterations
   * @param iterations The amount of iterations to divide by.
   * @return Returns the stringified percentage if count > 0.
   *         Else an Error is thrown.
   */
  def convertCountToProbability(summary: DrawSummary, iterations: Int): DrawProbabilitySummary = {
    summary.map { case (key, value) =>
      key -> value.map { case (k, v) => toPercentage(v, iterations) match {
        case None => throw new Error(s"Cant divide value $v by $iterations")
        case Some(percent) => k -> percent}}
      }
  }

  /** Prepends a percentage sign to the calculated value.
   *
   * @param value The value to be stringified and prepended.
   * @return Returns the stringified percentage.
   */
  private def appendPercentage(value: Double): String = {
    s"$value%"
  }

  /** Converts a double value to a percentage by dividing by given number of iterations.
   *
   * @param count      The count to be divided by iterations
   * @param iterations The amount of iterations to divide by.
   * @return Returns the stringified percentage if count > 0.
   *         Else an Error is thrown.
   */
  def convertCountToProbability(count: Double, iterations: Int): String = {
    toPercentage(count, iterations) match {
      case None => throw new Error(s"Cant divide value $count by $iterations")
      case Some(percent) => percent
    }
  }

  /** Converts a double value to a percentage by dividing by given number of iterations.
   *
   * @param count The count to be divided by iterations
   * @param iterations The amount of iterations to divide by.
   * @return Returns Some[String] if count can be divided ( count > 0 ).
   *         Else None is returned.
   */
  private def toPercentage(count: Double, iterations: Int): Option[String] = {
    count match {
      case x: Double if x <= 0 => None
      case _ => Some(appendPercentage(roundToPrecision(count / iterations * 100)))
    }
  }
}
