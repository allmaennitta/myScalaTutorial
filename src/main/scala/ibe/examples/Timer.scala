package ibe.examples

object Timer {
  def oncePerInterval(repetions: Int, pauseInterval: Int, callback: () => Unit) {
    for( i <- 1 to repetions){
      callback();
      Thread sleep pauseInterval
    }
  }
}