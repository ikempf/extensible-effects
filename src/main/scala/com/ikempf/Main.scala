package com.ikempf

import cats.data.Reader
import cats.data.Writer
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._
import cats.syntax.either._
import cats.syntax.option._
import org.atnos.eff.Fx.fx4

object Main extends App {

  private val option: Eff[Fx1[Option], Int] = fromOption(Option(5))

  private val either: Eff[Fx1[Either[String, ?]], Int] = fromEither(Left("string"))

  type _readerInt[R] = Reader[Int, ?] |= R
  type _writerString[R] = Writer[String, ?] |= R
  type _option[R] = Option |= R
  type _either[R] = Either[String, ?] |= R

  def test[R :_readerInt : _option : _either] = {
     ask[R, Int]
      .flatMap(i => send(Option(i).filter(_ == 5)))
      .flatMap(i => send(Option(i).filter(_ == 5)))
      .flatMap(i => send(Option(i).filter(_ == 5)))
      .flatMap(i => send(i.asRight[String]))
      .flatMap(i => send(i.asRight[String]))
      .flatMap(i => send(i.asRight[String]))
  }

  private val run = test[fx4[Reader[Int, ?], Writer[String, ?], Option, Either[String, ?]]]
  println(run.runReader(5).runWriter.runOption.runEither.run)

  type S = Fx.fx1[String Either ?]

  // compute with this stack
  val map: Map[String, Int] =
    Map("key1" -> 10, "key2" -> 20)

  // get 2 keys from the map and add the corresponding values
  def addKeys(key1: String, key2: String): Eff[S, Int] = for {
    a <- optionEither(map.get(key1), s"'$key1' not found")
    b <- optionEither(map.get(key2), s"'$key2' not found")
  } yield a + b

  println((addKeys("key1", "key2").runEither.run, addKeys("key1", "missing").runEither.run))

}
