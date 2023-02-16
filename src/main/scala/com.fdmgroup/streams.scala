//Stream imports
import akka.stream._
import akka.stream.scaladsl._

//Executing code samples
import akka.{ Done, NotUsed }
import akka.actor.ActorSystem
import akka.util.ByteString
import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths

object Main extends App {

////////////////////////Factorials/////////////////////////////////////////

  implicit val system: ActorSystem = ActorSystem("QuickStart")
  val source: Source[Int, NotUsed] = Source(1 to 100)
//   source.runForeach(i => println(i))

  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  val result: Future[IOResult] =
  factorials.map(num => ByteString(s"$num\n")).runWith(FileIO.toPath(Paths.get("factorials.txt")))
  factorials.runForeach(i => println(i))





///////////////Tweets///////////////////


// final case class Author(handle: String)

// final case class Hashtag(name: String)

// final case class Tweet(author: Author, timestamp: Long, body: String) {
//   def hashtags: Set[Hashtag] =
//     body
//       .split(" ")
//       .collect {
//         case t if t.startsWith("#") => Hashtag(t.replaceAll("[^#\\w]", ""))
//       }
//       .toSet
// }

// val akkaTag = Hashtag("#akka")

// val tweets: Source[Tweet, NotUsed] = Source(
//   Tweet(Author("rolandkuhn"), System.currentTimeMillis, "#akka rocks!") ::
//   Tweet(Author("patriknw"), System.currentTimeMillis, "#akka !") ::
//   Tweet(Author("bantonsson"), System.currentTimeMillis, "#akka !") ::
//   Tweet(Author("drewhk"), System.currentTimeMillis, "#akka !") ::
//   Tweet(Author("ktosopl"), System.currentTimeMillis, "#akka on the rocks!") ::
//   Tweet(Author("mmartynas"), System.currentTimeMillis, "wow #akka !") ::
//   Tweet(Author("akkateam"), System.currentTimeMillis, "#akka rocks!") ::
//   Tweet(Author("bananaman"), System.currentTimeMillis, "#bananas rock!") ::
//   Tweet(Author("appleman"), System.currentTimeMillis, "#apples rock!") ::
//   Tweet(Author("drama"), System.currentTimeMillis, "we compared #apples to #oranges!") ::
//   Nil)

//   implicit val system: ActorSystem = ActorSystem("reactive-tweets")

// println("Running first tweets app")
// println("")
// println("Filtering out akka hashtags")
// println("//////////////////////////////////")
//   tweets
//     .filterNot(_.hashtags.contains(akkaTag)) // Remove all tweets containing #akka hashtag
//     .map(_.hashtags) // Get all sets of hashtags ...
//     .reduce(_ ++ _) // ... and reduce them to a single set, removing duplicates across all tweets
//     .mapConcat(identity) // Flatten the set of hashtags to a stream of hashtags
//     .map(_.name.toUpperCase) // Convert all hashtags to upper case
//     .runWith(Sink.foreach(println)) // Attach the Flow to a Sink that will finally print the hashtags


//need to hit of sink first

// println("")
// println("Showing only akka hashtags")
// println("//////////////////////////////////")

//       tweets
//     .filter(_.hashtags.contains(akkaTag)) // Remove all tweets containing #akka hashtag
//     .map(_.hashtags) // Get all sets of hashtags ...
//     .reduce(_ ++ _) // ... and reduce them to a single set, removing duplicates across all tweets
//     .mapConcat(identity) // Flatten the set of hashtags to a stream of hashtags
//     .map(_.name.toUpperCase) // Convert all hashtags to upper case
//     .runWith(Sink.foreach(println)) // Attach the Flow to a Sink that will finally print the hashtags





////////////////////////Flow and Sink/////////////////////////////////////////



  // def lineSink(filename: String): Sink[String, Future[IOResult]] =
  //   Flow[String]
  //     .map(s => ByteString(s + "\n"))
  //     .toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)

  
  // implicit val system: ActorSystem = ActorSystem("QuickStart")
  // val source: Source[Int, NotUsed] = Source(1 to 100)
  // val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  // factorials.map(_.toString).runWith(lineSink("factorial2.txt"))

  // factorials
  //   .zipWith(Source(0 to 100))((num, idx) => s"$idx! = $num")
  //   .throttle(1, 1.second)
  //   .runForeach(println)



////////////////////////////internal backpressure signals ///////////////////


// final case class Author(handle: String)

// final case class Hashtag(name: String)

// final case class Tweet(author: Author, timestamp: Long, body: String) {
//   def hashtags: Set[Hashtag] =
//     body
//       .split(" ")
//       .collect {
//         case t if t.startsWith("#") => Hashtag(t.replaceAll("[^#\\w]", ""))
//       }
//       .toSet
// }

// val akkaTag = Hashtag("#akka")


// implicit val system: ActorSystem = ActorSystem("reactive-tweets")


////////////////////////  Graphs  /////////////////////////////////////////





  "simple broadcast" in {
    val writeAuthors: Sink[Author, Future[Done]] = Sink.ignore
    val writeHashtags: Sink[Hashtag, Future[Done]] = Sink.ignore

    // format: OFF
    //#graph-dsl-broadcast
    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._

      val bcast = b.add(Broadcast[Tweet](2))
      tweets ~> bcast.in
      bcast.out(0) ~> Flow[Tweet].map(_.author) ~> writeAuthors
      bcast.out(1) ~> Flow[Tweet].mapConcat(_.hashtags.toList) ~> writeHashtags
      ClosedShape
    })
    g.run()
}




}