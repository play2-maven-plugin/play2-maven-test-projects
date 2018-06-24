import java.io.File

import org.scalatestplus.play.FakeApplicationFactory
import play.api._
import play.api.inject._

trait MyApplicationFactory extends FakeApplicationFactory {

  override def fakeApplication: Application = {
    val env = Environment.simple(new File("."))
    val configuration = Configuration.load(env)
    val context = ApplicationLoader.Context(
      environment = env,
      initialConfiguration = configuration,
      lifecycle = new DefaultApplicationLifecycle(),
      devContext = None
    )
    val loader = new MyApplicationLoader()
    loader.load(context)
  }

}
