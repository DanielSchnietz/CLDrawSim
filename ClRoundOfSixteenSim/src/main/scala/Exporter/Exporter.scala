package Exporter

abstract class Exporter() {
  def exportData[A](data: A):Unit
}
