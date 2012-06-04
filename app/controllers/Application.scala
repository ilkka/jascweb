package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.JsValue
import com.codahale.jerkson.Json
import anorm._

import models._

object Application extends Controller {
  
  	def index = Action {
		Ok(views.html.index())
  	}

	def tasks = Action {
		val tasks = Task.all()
		val json = Json.generate(tasks)
		Ok(json).as("application/json")
	}

	def saveTasks = Action { implicit request =>
		val json: Option[JsValue] = request.body.asJson
		json.map { json =>
			val tasklist = (json \ "tasks").as[List[JsValue]]
			tasklist.map { taskJson =>
				val id = (taskJson \ "id").asOpt[Long]
				val label = (taskJson \ "label").as[String]
				val destroy = (taskJson \ "_destroy").asOpt[Boolean]
				if (id.isDefined) {
					if (destroy.getOrElse(false)) {
						Task.delete(id.get)
					} else {	
						// update
					}
				} else {
					models.Task.create(label)
				}
			}
			Ok(Json.generate(Task.all())).as("application/json")
		}.getOrElse {
			BadRequest("Expecting plain text body")
		}
	}
}