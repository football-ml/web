package utils

import controllers.routes
import play.twirl.api.Html

object RessourceUtil {
  private val CSS_FOLDER = "css"
  private val JS_FOLDER = "js"

  // Use String.format until https://issues.scala-lang.org/browse/SI-6476 is fixed
  def js(filename: String) = {
    val src: String = routes.Assets.at(s"$JS_FOLDER/$filename").toString
    val template = "<script type=\"text/javascript\" src=\"%s\"></script>"
    Html(template.format(src))
    //val template = s"<script type=\"text/javascript\" src=\"$src\"></script>"
  }

  def css(filename: String) = {
    val src: String = routes.Assets.at(s"$CSS_FOLDER/$filename").toString
    val template = " <link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"%s\">"
    Html(template.format(src))
  }

  def getRelativeURL(filePathRelativeToPublic: String) = {
    routes.Assets.at(filePathRelativeToPublic).toString
  }
}
