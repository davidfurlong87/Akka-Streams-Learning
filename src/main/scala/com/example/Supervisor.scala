
// import akka.actor.typed.PostStop
// import akka.actor.typed.PreRestart
// import akka.actor.typed.Signal
// import akka.actor.typed.SupervisorStrategy

// //#print-refs
// import akka.actor.typed.ActorSystem
// import akka.actor.typed.Behavior
// import akka.actor.typed.scaladsl.AbstractBehavior
// import akka.actor.typed.scaladsl.ActorContext
// import akka.actor.typed.scaladsl.Behaviors

// object SupervisingActor {
//   def apply(): Behavior[String] =
//     Behaviors.setup(context => new SupervisingActor(context))
// }

// class SupervisingActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
//   private val child = context.spawn(
//     Behaviors.supervise(SupervisedActor()).onFailure(SupervisorStrategy.restart),
//     name = "supervised-actor")

//   override def onMessage(msg: String): Behavior[String] =
//     msg match {
//       case "failChild" =>
//         child ! "fail"
//         this
//     }
// }

// object SupervisedActor {
//   def apply(): Behavior[String] =
//     Behaviors.setup(context => new SupervisedActor(context))
// }

// class SupervisedActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {
//   println("supervised actor started")

//   override def onMessage(msg: String): Behavior[String] =
//     msg match {
//       case "fail" =>
//         println("supervised actor fails now")
//         throw new Exception("I failed!")
//     }

//   override def onSignal: PartialFunction[Signal, Behavior[String]] = {
//     case PreRestart =>
//       println("supervised actor will be restarted")
//       this
//     case PostStop =>
//       println("supervised actor stopped")
//       this
//   }

// }

// object Main {
//   def apply(): Behavior[String] =
//     Behaviors.setup(context => new Main(context))

// }

// class Main(context: ActorContext[String]) extends AbstractBehavior[String](context) {
//   override def onMessage(msg: String): Behavior[String] =
//     msg match {
//       case "start" =>
//         val first = context.spawn(SupervisingActor(), "first")
//             first ! "failChild"
//             println(s"First: $first")
        
//         this
//     }
// }

// object Runner extends App {
//   val testSystem = ActorSystem(Main(), "testSystem")
//   testSystem ! "start"
// }


