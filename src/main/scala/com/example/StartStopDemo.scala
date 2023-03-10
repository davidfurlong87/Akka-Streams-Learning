
// import akka.actor.typed.PostStop
// import akka.actor.typed.PreRestart
// import akka.actor.typed.Signal
// import akka.actor.typed.SupervisorStrategy
// import akka.actor.typed.ActorSystem
// import akka.actor.typed.Behavior
// import akka.actor.typed.scaladsl.AbstractBehavior
// import akka.actor.typed.scaladsl.ActorContext
// import akka.actor.typed.scaladsl.Behaviors

// object StartStopActor1 {
//   def apply(): Behavior[String] =
//     Behaviors.setup(context => new StartStopActor1(context))
// }

// class StartStopActor1(context: ActorContext[String]) extends AbstractBehavior[String](context) {
//   println("first started")
//   context.spawn(StartStopActor2(), "second")

//   override def onMessage(msg: String): Behavior[String] =
//     msg match {
//       case "stop" => Behaviors.stopped
//     }

//   override def onSignal: PartialFunction[Signal, Behavior[String]] = {
//     case PostStop =>
//       println("first stopped")
//       this
//   }

// }

// object StartStopActor2 {
//   def apply(): Behavior[String] =
//     Behaviors.setup(new StartStopActor2(_))
// }

// class StartStopActor2(context: ActorContext[String]) extends AbstractBehavior[String](context) {
//   println("second started")

//   override def onMessage(msg: String): Behavior[String] = {
//     // no messages handled by this actor
//     Behaviors.unhandled
//   }

//   override def onSignal: PartialFunction[Signal, Behavior[String]] = {
//     case PostStop =>
//       println("second stopped")
//       this
//   }

// }
// object StartStopMain {
//   def apply(): Behavior[String] =
//     Behaviors.setup(context => new Main(context))

// }

// class StartStopMain(context: ActorContext[String]) extends AbstractBehavior[String](context) {
//   override def onMessage(msg: String): Behavior[String] =
//     msg match {
//       case "start" =>
//         val first = context.spawn(StartStopActor1(), "first")
//             first ! "stop"
//             println(s"First: $first")
        
//         this
//     }
// }

// object StartStopRunner extends App {
//   val testSystem = ActorSystem(StartStopMain(), "testSystem")
//   testSystem ! "start"
// }
